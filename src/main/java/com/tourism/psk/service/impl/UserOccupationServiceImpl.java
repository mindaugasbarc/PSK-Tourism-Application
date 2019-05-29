package com.tourism.psk.service.impl;

import com.tourism.psk.exception.InvalidTimePeriodException;
import com.tourism.psk.exception.UserNotFoundException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.repository.UserOccupationRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserOccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserOccupationServiceImpl implements UserOccupationService {
    private UserOccupationRepository userOccupationRepository;
    private UserRepository userRepository;

    @Value("${date-format}")
    private String dateFormat;

    @Autowired
    public UserOccupationServiceImpl(UserOccupationRepository userOccupationRepository,
                                     UserRepository userRepository) {
        this.userOccupationRepository = userOccupationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void markAvailability(long id, TimePeriodRequest timePeriod, boolean isAvailable) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date start = format.parse(timePeriod.getFrom());
            Date end = format.parse(timePeriod.getTo());
            if (start.after(end)) {
                throw new InvalidTimePeriodException();
            }
            List<UserOccupation> occupations = userOccupationRepository.getOccupationsInPeriod(id, start, end);
            if (isAvailable) {
                if (occupations.size() == 1) {
                    fixSingleOccupation(occupations.get(0), start, end);
                }
                else if (occupations.size() > 1) {
                    fixMultipleOccupations(occupations, start, end);
                }
            }
            else {
                markUserAsUnavailable(occupations, start, end, user);
            }
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Incorrect date format. Use: YYYY-MM-DD");
        }
    }

    @Override
    public List<UserOccupation> getOccupationsInPeriod(long id, String from, String to) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return userOccupationRepository.getOccupationsInPeriod(id, format.parse(from), format.parse(to));
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Incorrect date format. Use: YYYY-MM-DD");
        }
    }

    @Override
    public void markAvailability(long id, String from, String to, boolean status) {
        markAvailability(id, new TimePeriodRequest(from, to), status);
    }

    private void fixSingleOccupation(UserOccupation occupation, Date start, Date end) {
        if ( !(start.after(occupation.getFrom())) && !(end.before(occupation.getTo())) ) {
            userOccupationRepository.delete(occupation);
        }
        else if (start.after(occupation.getFrom()) && end.before(occupation.getTo())){
            List<UserOccupation> newOccupations =  splitOccupation(occupation, start, end);
            userOccupationRepository.delete(occupation);
            userOccupationRepository.saveAll(newOccupations);
        }
        else {
            shrinkOccupation(occupation, start, end);
            userOccupationRepository.save(occupation);
        }
    }

    private void fixMultipleOccupations(List<UserOccupation> occupations, Date start, Date end) {
        if (start.before(occupations.get(0).getFrom())){
            userOccupationRepository.delete(occupations.get(0));
        }
        else {
            shrinkOccupation(occupations.get(0), start, end);
        }

        if (end.after(occupations.get(occupations.size() - 1).getTo())) {
            userOccupationRepository.delete(occupations.get(occupations.size() - 1));
        }
        else {
            shrinkOccupation(occupations.get(occupations.size() - 1), start, end);
        }

        List<UserOccupation> deletions = occupations.subList(1, occupations.size() - 1);
        userOccupationRepository.deleteAll(deletions);
    }

    private void markUserAsUnavailable(List<UserOccupation> occupations, Date start, Date end, User user) {
        if (occupations.size() == 1) {
            UserOccupation occupation = occupations.get(0);
            if (occupation.getFrom().after(start) || occupation.getTo().before(end)) {
                extendOccupation(occupation, start, end);
            }
        }
        else if (occupations.size() > 1) {
            UserOccupation joined = joinOccupations(occupations);
            userOccupationRepository.deleteAll(occupations);
            extendOccupation(joined, start, end);
            userOccupationRepository.save(joined);
        }
        else {
            UserOccupation occupation = new UserOccupation(start, end);
            occupation.setUser(user);
            userOccupationRepository.save(occupation);
        }
    }


    private void shrinkOccupation(UserOccupation occupation, Date start, Date end) {
        if (start.after(occupation.getFrom())) {
            occupation.setTo(addDays(start, -1));
        }
        else if (end.before(occupation.getTo())) {
            occupation.setFrom(addDays(end, 1));
        }
    }

    private List<UserOccupation> splitOccupation(UserOccupation occupation, Date start, Date end) {
        List<UserOccupation> newOccupations = new ArrayList<UserOccupation>(){{
            add(new UserOccupation(occupation.getFrom(), addDays(start, -1)));
            add(new UserOccupation(addDays(end, 1), occupation.getTo()));
        }};
        newOccupations.get(0).setUser(occupation.getUser());
        newOccupations.get(1).setUser(occupation.getUser());
        return newOccupations;
    }

    private void extendOccupation(UserOccupation occupation, Date start, Date end) {
        occupation.setFrom(start.before(occupation.getFrom()) ? start : occupation.getFrom());
        occupation.setTo(end.after(occupation.getTo()) ? end : occupation.getTo());
    }

    private UserOccupation joinOccupations(List<UserOccupation> occupations) {
        Date start = occupations.get(0).getFrom();
        Date end = occupations.get(occupations.size() - 1).getTo();
        UserOccupation joinedOccupation = new UserOccupation(start, end);
        joinedOccupation.setUser(occupations.get(0).getUser());
        return joinedOccupation;
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}

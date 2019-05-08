package com.tourism.psk.service.impl;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.*;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;
import com.tourism.psk.repository.UserOccupationRepository;
import com.tourism.psk.repository.UserLoginRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;
    private UserOccupationRepository userOccupationRepository;

    @Value("${date-format}")
    private String dateFormat;
    @Value("${min-username-length}")
    private int minUsernameLength;
    @Value("${min-password-length}")
    private int minPasswordLength;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserLoginRepository userLoginRepository,
                           UserOccupationRepository userOccupationRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userOccupationRepository = userOccupationRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean userExists(String username, String email) {
        return userRepository.exists(username, email);
    }

    @Override
    public User login(UserLogin userLogin) {
        Long userId = userLoginRepository.findUserIdByUsernameAndPassword(
                userLogin.getUsername(),
                userLogin.getPassword()
        );
        if (userId == null) {
            throw new LoginException();
        }
        return userRepository.findById((long) userId);
    }

    @Override
    public User register(UserRegistrationRequest userDetails) {
        validateUserRegistrationData(userDetails);
        UserLogin userLogin = new UserLogin(userDetails.getUsername(), userDetails.getPassword());
        User user = new User(
                userDetails.getFullname(),
                userDetails.getEmail(),
                userDetails.getRole() != null ? userDetails.getRole() : UserRole.DEFAULT
        );
        user.setUserLogin(userLogin);
        if (!userExists(userLogin.getUsername(), user.getEmail())) {
            userLogin.setUser(user);
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    @Transactional
    public User update(User user, long id) {
        if (user.getFullname() == null || user.getFullname().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty() ||
            user.getRole() == null) {
            throw new ValueNotProvidedException();
        }
        userRepository.updateById(id, user.getFullname(), user.getEmail(), user.getRole());
        return userRepository.findById(id);
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
                    UserOccupation occupation = occupations.get(0);
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
                else if (occupations.size() > 1) {
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
            }
            else {
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
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Incorrect date format. Use: YYYY-MM-DD");
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

    private void validateUserRegistrationData(UserRegistrationRequest userDetails) {
        if (userDetails.getUsername().length() < minUsernameLength) {
            throw new IllegalArgumentException("Username must be at least " + minUsernameLength + " characters");
        }
        if (userDetails.getPassword().length() < minPasswordLength) {
            throw new IllegalArgumentException("Password must be at least " + minPasswordLength + " characters");
        }
        if (userDetails.getEmail().length() == 0) {
            throw new IllegalArgumentException("Email field must not be empty");
        }
    }
}

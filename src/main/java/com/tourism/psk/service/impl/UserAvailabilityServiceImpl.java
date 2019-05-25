package com.tourism.psk.service.impl;

import com.tourism.psk.model.User;
import com.tourism.psk.repository.UserOccupationRepository;
import com.tourism.psk.service.UserAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UserAvailabilityServiceImpl implements UserAvailabilityService {

    private final UserOccupationRepository userOccupationRepository;
    private final SimpleDateFormat dateFormat;

    @Autowired
    public UserAvailabilityServiceImpl(UserOccupationRepository userOccupationRepository,
                                       @Value("${date-format}") String dateFormat) {
        this.userOccupationRepository = userOccupationRepository;
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public void validateUserAvailability(final User user, final String fromDate, final String toDate) {
        try {
            if(!userOccupationRepository.getOccupationsInPeriod(user.getId(), dateFormat.parse(fromDate), dateFormat.parse(toDate)).isEmpty()) {
                throw new RuntimeException("user " + user + " is occupied at the time");
            }

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}

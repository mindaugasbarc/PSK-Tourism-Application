package com.tourism.psk.validator.impl;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.service.UserAvailabilityService;
import com.tourism.psk.validator.GroupTripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupTripValidatorImpl implements GroupTripValidator {

    private final UserAvailabilityService userAvailabilityService;

    @Autowired
    public GroupTripValidatorImpl(UserAvailabilityService userAvailabilityService) {
        this.userAvailabilityService = userAvailabilityService;
    }

    @Override
    public void validateGroupTrip(GroupTrip groupTrip) {
        userAvailabilityService.validateUserAvailability(groupTrip.getAdvisor(), groupTrip.getDateFrom(), groupTrip.getDateTo());
        groupTrip.getTrips().stream()
                .map(Trip::getUser)
                .forEach(user -> userAvailabilityService
                        .validateUserAvailability(user, groupTrip.getDateFrom(), groupTrip.getDateTo()));

    }
}

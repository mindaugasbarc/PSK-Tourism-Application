package com.tourism.psk.validator.impl;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.service.HouseRoomAvailabilityService;
import com.tourism.psk.service.UserAvailabilityService;
import com.tourism.psk.validator.GroupTripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GroupTripValidatorImpl implements GroupTripValidator {

    private final UserAvailabilityService userAvailabilityService;
    private final HouseRoomAvailabilityService houseRoomAvailabilityService;

    @Autowired
    public GroupTripValidatorImpl(UserAvailabilityService userAvailabilityService, HouseRoomAvailabilityService houseRoomAvailabilityService) {
        this.userAvailabilityService = userAvailabilityService;
        this.houseRoomAvailabilityService = houseRoomAvailabilityService;
    }

    @Override
    public void validateGroupTrip(final GroupTrip groupTrip) {

        Set<Trip> trips = groupTrip.getUserTrips();
        trips.stream()
                .map(Trip::getUser)
                .forEach(user -> userAvailabilityService
                        .validateUserAvailability(user, groupTrip.getDateFrom(), groupTrip.getDateTo()));

        groupTrip.getUserTrips().stream().map(Trip::getHouserooms).flatMap(List::stream)
                .forEach(houseRoom -> houseRoomAvailabilityService.validateHouseRoomAvailability(houseRoom, groupTrip.getDateFrom(), groupTrip.getDateTo()));


    }
}

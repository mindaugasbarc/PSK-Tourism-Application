package com.tourism.psk.service;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.User;

import java.util.List;

public interface HouseRoomAvailabilityService {

    void validateHouseRoomAvailability(OfficeRoom officeRoom, String fromDate, String toDate);
    void addHouseRoomAvailabilitiesIfValid(List<OfficeRoom> officeRooms, User user, String fromDate, String toDate);
    void removeHouseRoomAvailabilities(GroupTrip groupTrip);
}

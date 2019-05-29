package com.tourism.psk.service;

import com.tourism.psk.model.OfficeRoom;

public interface HouseRoomAvailabilityService {

    void validateHouseRoomAvailability(OfficeRoom officeRoom, String fromDate, String toDate);
}

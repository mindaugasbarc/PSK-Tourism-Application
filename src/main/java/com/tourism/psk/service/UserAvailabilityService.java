package com.tourism.psk.service;

import com.tourism.psk.model.User;

public interface UserAvailabilityService {

    void validateUserAvailability(User user, String fromDate, String toDate);
}

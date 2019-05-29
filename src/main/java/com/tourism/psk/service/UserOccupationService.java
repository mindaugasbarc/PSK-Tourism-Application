package com.tourism.psk.service;

import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.TimePeriodRequest;

import java.util.List;

public interface UserOccupationService {
    void markAvailability(long id, TimePeriodRequest timePeriod, boolean status);
    List<UserOccupation> getOccupationsInPeriod(long id, String from, String to);
    void markAvailability(long id, String from, String to, boolean status);
}

package com.tourism.psk.service;

import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    boolean userExists(String username, String email);
    User login(UserLogin userLogin);
    User register(UserRegistrationRequest userDetails);
    User update(User user, long id);
    List<UserOccupation> getOccupationsInPeriod(long id, String from, String to);
    void markAvailability(long id, TimePeriodRequest timePeriod, boolean status);
}

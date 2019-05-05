package com.tourism.psk.service;

import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;

import java.util.List;

public interface UserService {
    User getUser(long id);
    List<User> getAllUsers();
    boolean userExists(String username, String email);
    boolean isAvailable(long userId, TimePeriodRequest timePeriod);
    User login(UserLogin userLogin);
    User register(UserRegistrationRequest userDetails);
    User update(User user, long id);
}

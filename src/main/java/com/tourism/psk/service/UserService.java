package com.tourism.psk.service;

import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.UserRegistrationRequest;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    boolean userExists(String username, String email);
    User login(UserLogin userLogin);
    User register(UserRegistrationRequest userDetails, User initiator);
    User update(User user, long id);
    void delete(long userId, User initiatedBy);
}

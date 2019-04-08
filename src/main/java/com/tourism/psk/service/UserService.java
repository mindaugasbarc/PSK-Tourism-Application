package com.tourism.psk.service;

import com.tourism.psk.model.User;

import java.util.Date;

public interface UserService {
    User save(User user);
    User getUser(long id);
    boolean userExists(String username, String email);
    boolean isAvailable(long userId, Date from, Date to);
    long login(String username, String password);
}

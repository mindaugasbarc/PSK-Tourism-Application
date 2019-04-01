package com.tourism.psk.service;

import com.tourism.psk.model.User;

public interface UserService {
    User save(User user);
    boolean isValidCredential(String username, String password);
    User getUser(String username);
    boolean userExists(String username, String email);
}

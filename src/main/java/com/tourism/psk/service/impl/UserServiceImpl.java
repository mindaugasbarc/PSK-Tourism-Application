package com.tourism.psk.service.impl;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.UserAlreadyExistsException;
import com.tourism.psk.exception.UserNotFoundException;
import com.tourism.psk.model.User;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (!userExists(user.getUsername(), user.getEmail())) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            user.setRole((user.getRole() == null) ? UserRole.USER : user.getRole());
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    public boolean isValidCredential(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return BCrypt.checkpw(password, user.getPassword());
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean userExists(String username, String email) {
        return userRepository.exists(username, email);
    }
}

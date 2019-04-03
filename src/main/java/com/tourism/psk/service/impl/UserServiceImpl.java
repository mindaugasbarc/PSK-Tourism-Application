package com.tourism.psk.service.impl;

import com.tourism.psk.exception.LoginException;
import com.tourism.psk.exception.UserAlreadyExistsException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.repository.UserLoginRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserLoginRepository userLoginRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public User save(User user) {
        UserLogin userLogin = user.getUserLogin();
        if (!userExists(userLogin.getUsername(), user.getEmail())) {
            userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
            userLogin.setUser(user);
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    public boolean isValidCredential(String username, String password) {
        UserLogin userLogin = userLoginRepository.findByUsername(username);
        if (userLogin == null) {
            throw new LoginException();
        }
        return passwordEncoder.matches(password, userLogin.getPassword());
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

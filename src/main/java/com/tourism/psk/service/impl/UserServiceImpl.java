package com.tourism.psk.service.impl;

import com.tourism.psk.exception.LoginException;
import com.tourism.psk.exception.UserAlreadyExistsException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.repository.OccupationRepository;
import com.tourism.psk.repository.UserLoginRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;
    private OccupationRepository occupationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserLoginRepository userLoginRepository,
                           OccupationRepository occupationRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.occupationRepository = occupationRepository;
    }

    @Override
    public User save(User user) {
        UserLogin userLogin = user.getUserLogin();
        if (!userExists(userLogin.getUsername(), user.getEmail())) {
            userLogin.setUser(user);
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean userExists(String username, String email) {
        return userRepository.exists(username, email);
    }

    @Override
    public boolean isAvailable(long userId, Date from, Date to) {
        return !occupationRepository.hasOccupations(userId, from, to);
    }

    @Override
    public long login(String username, String password) {
        Long userId = userLoginRepository.findUserIdByUsernameAndPassword(username, password);
        if (userId == null) {
            throw new LoginException();
        }
        return userId;
    }
}

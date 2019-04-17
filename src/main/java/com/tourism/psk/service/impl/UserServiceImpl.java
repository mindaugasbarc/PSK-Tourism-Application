package com.tourism.psk.service.impl;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.LoginException;
import com.tourism.psk.exception.UserAlreadyExistsException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;
import com.tourism.psk.repository.OccupationRepository;
import com.tourism.psk.repository.UserLoginRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;
    private OccupationRepository occupationRepository;

    @Value("${date-format}")
    private String dateFormat;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserLoginRepository userLoginRepository,
                           OccupationRepository occupationRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.occupationRepository = occupationRepository;
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
    public boolean isAvailable(long userId, TimePeriodRequest timePeriod) {
        try {
            DateFormat format = new SimpleDateFormat(dateFormat);
            Date from = format.parse(timePeriod.getFrom());
            Date to = format.parse(timePeriod.getTo());
            return !occupationRepository.hasOccupations(userId, from, to);
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Incorrect date format. Use: YYYY-MM-DD");
        }
    }

    @Override
    public User login(UserLogin userLogin) {
        Long userId = userLoginRepository.findUserIdByUsernameAndPassword(
                userLogin.getUsername(),
                userLogin.getPassword()
        );
        if (userId == null) {
            throw new LoginException();
        }
        return userRepository.findById((long) userId);
    }

    @Override
    public User register(UserRegistrationRequest userDetails) {
        UserLogin userLogin = new UserLogin(userDetails.getUsername(), userDetails.getPassword());
        User user = new User(userDetails.getFullname(), userDetails.getEmail(), UserRole.DEFAULT);
        user.setUserLogin(userLogin);
        if (!userExists(userLogin.getUsername(), user.getEmail())) {
            userLogin.setUser(user);
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }
}

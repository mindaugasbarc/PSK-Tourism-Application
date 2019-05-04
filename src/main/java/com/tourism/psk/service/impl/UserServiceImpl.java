package com.tourism.psk.service.impl;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.LoginException;
import com.tourism.psk.exception.UserAlreadyExistsException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;
import com.tourism.psk.repository.UserOccupationRepository;
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
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;
    private UserOccupationRepository userOccupationRepository;

    @Value("${date-format}")
    private String dateFormat;
    @Value("${min-username-length}")
    private int minUsernameLength;
    @Value("${min-password-length}")
    private int minPasswordLength;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserLoginRepository userLoginRepository,
                           UserOccupationRepository userOccupationRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userOccupationRepository = userOccupationRepository;
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
            return !userOccupationRepository.hasOccupations(userId, from, to);
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
        validateUserRegistrationData(userDetails);
        UserLogin userLogin = new UserLogin(userDetails.getUsername(), userDetails.getPassword());
        User user = new User(
                userDetails.getFullname(),
                userDetails.getEmail(),
                userDetails.getRole() != null ? userDetails.getRole() : UserRole.DEFAULT
        );
        user.setUserLogin(userLogin);
        if (!userExists(userLogin.getUsername(), user.getEmail())) {
            userLogin.setUser(user);
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException();
    }

    private void validateUserRegistrationData(UserRegistrationRequest userDetails) {
        if (userDetails.getUsername().length() < minUsernameLength) {
            throw new IllegalArgumentException("Username must be at least " + minUsernameLength + " characters");
        }
        if (userDetails.getPassword().length() < minPasswordLength) {
            throw new IllegalArgumentException("Password must be at least " + minPasswordLength + " characters");
        }
        if (userDetails.getEmail().length() == 0) {
            throw new IllegalArgumentException("Email field must not be empty");
        }
    }
}

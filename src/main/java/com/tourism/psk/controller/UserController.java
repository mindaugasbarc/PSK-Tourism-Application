package com.tourism.psk.controller;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.LoginException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User registerUser(@RequestBody Map<String, String> userDetails) {
        UserLogin userLogin = new UserLogin(userDetails.get("username"), userDetails.get("password"));
        User user = new User(userDetails.get("fullname"), userDetails.get("email"), UserRole.USER);
        user.setUserLogin(userLogin);
        return userService.save(user);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public User login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        if (userService.isValidCredential(credentials.get("username"), credentials.get("password"))) {
            User user = userService.getUser(credentials.get("username"));
            response.addHeader("Authorization", "Bearer " + sessionService.create(user.getId()).getToken());
            return user;
        }
        else throw new LoginException();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUserByAccessToken(@RequestHeader("Authorization") String header) {
        return sessionService.authenticate(header.replace("Bearer ", "")).getUser();
    }
}

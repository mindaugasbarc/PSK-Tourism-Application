package com.tourism.psk.controller;

import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    private SessionService sessionService;

    @Value("${auth-header-name}")
    private String authHeaderName;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.CREATED)
    @CrossOrigin
    public User registerUser(@RequestBody UserRegistrationRequest userDetails) {
        return userService.register(userDetails);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @CrossOrigin
    public User login(@RequestBody UserLogin userLogin, HttpServletResponse response) {
        User user = userService.login(userLogin);
        response.addHeader(authHeaderName, sessionService.create(user.getId()).getToken());
        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @CrossOrigin
    public User getUserByAccessToken(HttpServletRequest request) {
        return sessionService.authenticate(request.getHeader(authHeaderName)).getUser();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    @CrossOrigin
    public boolean getUserAvailabilityStatus(@RequestBody TimePeriodRequest timePeriod,
                                             @PathVariable long id,
                                             HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return userService.isAvailable(id,timePeriod);
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    @CrossOrigin
    public List<User> getAllUsers(HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return userService.getAllUsers();
    }
}

package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.Session;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.model.UserOccupation;
import com.tourism.psk.model.request.TimePeriodRequest;
import com.tourism.psk.model.request.UserRegistrationRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.UserOccupationService;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;
    private final UserOccupationService userOccupationService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService, UserOccupationService userOccupationService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.userOccupationService = userOccupationService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User registerUser(@RequestBody UserRegistrationRequest userDetails,
                             @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return userService.register(userDetails, session.getUser());
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public User login(@RequestBody UserLogin userLogin, HttpServletResponse response) {
        User user = userService.login(userLogin);
        response.addHeader(Globals.ACCESS_TOKEN_HEADER_NAME, sessionService.create(user.getId()).getToken());
        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUserByAccessToken(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        return sessionService.authenticate(authToken).getUser();
    }

    @RequestMapping(value = "/user/{id}/availability", method = RequestMethod.GET)
    public List<UserOccupation> getUserAvailabilityStatus(@RequestParam String from,
                                                          @RequestParam String to,
                                                          @PathVariable long id,
                                                          @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return userOccupationService.getOccupationsInPeriod(id, from, to);
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User updateUser(@PathVariable long id,
                           @RequestBody User user,
                           @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return userService.update(user, id);
    }

    @RequestMapping(value = "/user/{id}/availability", method = RequestMethod.POST)
    public void updateUserAvailability(@PathVariable long id,
                                       @RequestParam(defaultValue = "false") boolean status,
                                       @RequestBody TimePeriodRequest timePeriod,
                                       @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        userOccupationService.markAvailability(id, timePeriod, status);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId, @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        userService.delete(userId,sessionService.authenticate(authToken).getUser());
    }
}

package com.tourism.psk.controller;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.model.User;
import com.tourism.psk.model.UserLogin;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private SessionService sessionService;

    @Value("${auth-header-prefix}")
    private String headerPrefix;

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
    public Map<String, Object> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        long userId = userService.login(credentials.get("username"), credentials.get("password"));
        User user = userService.getUser(userId);
        response.addHeader("Authorization", headerPrefix + " " + sessionService.create(userId).getToken());
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", user.getId());
        responseBody.put("fullname", user.getFullname());
        responseBody.put("email", user.getEmail());
        responseBody.put("role", user.getRole().toString());
        return responseBody;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUserByAccessToken(@RequestHeader("Authorization") String header) {
        return sessionService.authenticate(header).getUser();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public boolean getUserAvailabilityStatus(@RequestBody Map<String, String> range,
                                             @PathVariable long id,
                                             @RequestHeader("Authorization") String header) throws ParseException {
        sessionService.authenticate(header);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = dateFormat.parse(range.get("from"));
        Date end = dateFormat.parse(range.get("to"));
        return userService.isAvailable(id, start, end);
    }
}

package com.tourism.psk.controller;

import com.sun.deploy.net.HttpResponse;
import com.tourism.psk.exception.LoginException;
import com.tourism.psk.model.User;
import com.tourism.psk.model.response.UserResponse;
import com.tourism.psk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody User user) {
        userService.save(user);
        return new UserResponse(user);
    }

    @RequestMapping(value = "user/login", method = RequestMethod.POST)
    public UserResponse loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        if (userService.isValidCredential(username, password)) {
             return new UserResponse(userService.getUser(username));
        }
        throw new LoginException();
    }
}

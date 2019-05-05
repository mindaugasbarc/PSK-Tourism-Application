package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.Office;
import com.tourism.psk.service.OfficeService;
import com.tourism.psk.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class OfficeController {
    private OfficeService officeService;
    private SessionService sessionService;

    @Autowired
    public OfficeController(OfficeService officeService, SessionService sessionService) {
        this.officeService = officeService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public List<Office> getAll(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return officeService.findAll();
    }

    @RequestMapping(value = "/office/{id}", method = RequestMethod.GET)
    public Office getById(@PathVariable("id") long id, @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return officeService.find(id);
    }
}

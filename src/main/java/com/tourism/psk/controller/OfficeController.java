package com.tourism.psk.controller;

import com.tourism.psk.model.Office;
import com.tourism.psk.model.Session;
import com.tourism.psk.service.OfficeService;
import com.tourism.psk.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfficeController {
    private OfficeService officeService;
    private SessionService sessionService;

    @Autowired
    public OfficeController(OfficeService officeService, SessionService sessionService) {
        this.officeService = officeService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    @CrossOrigin
    public List<Office> getAll(@RequestHeader("Authorization") String header) {
        sessionService.authenticate(header);
        return officeService.findAll();
    }

    @RequestMapping(value = "/office/{id}", method = RequestMethod.GET)
    @CrossOrigin
    public Office getById(@PathVariable("id") long id, @RequestHeader("Authorization") String header) {
        sessionService.authenticate(header);
        return officeService.find(id);
    }
}

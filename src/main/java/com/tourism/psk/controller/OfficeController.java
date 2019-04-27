package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.Office;
import com.tourism.psk.model.Session;
import com.tourism.psk.service.OfficeService;
import com.tourism.psk.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OfficeController {
    private OfficeService officeService;
    private SessionService sessionService;

    @Value("${auth-header-name}")
    private String authHeaderName;

    @Autowired
    public OfficeController(OfficeService officeService, SessionService sessionService) {
        this.officeService = officeService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public List<Office> getAll(HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return officeService.findAll();
    }

    @RequestMapping(value = "/office/{id}", method = RequestMethod.GET)
    public Office getById(@PathVariable("id") long id, HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return officeService.find(id);
    }
}

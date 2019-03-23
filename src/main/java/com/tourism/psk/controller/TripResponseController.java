package com.tourism.psk.controller;

import com.tourism.psk.model.response.TripResponse;
import com.tourism.psk.service.TripResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripResponseController {

    private final TripResponseService tripResponseService;

    @Autowired
    public TripResponseController(TripResponseService tripResponseService) {
        this.tripResponseService = tripResponseService;
    }


    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public List<TripResponse> findTrips() {
        return tripResponseService.findAll();
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public TripResponse findTrip(@PathVariable("id") long id) {
        return tripResponseService.find(id);
    }
 }

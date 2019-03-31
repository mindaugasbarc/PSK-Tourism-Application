package com.tourism.psk.controller;

import com.tourism.psk.model.response.Trip;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripResponseController {

    private final TripService tripResponseService;

    @Autowired
    public TripResponseController(TripService tripResponseService) {
        this.tripResponseService = tripResponseService;
    }


    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public List<Trip> findTrips() {
        return tripResponseService.findAll();
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public Trip findTrip(@PathVariable("id") long id) {
        return tripResponseService.find(id);
    }

    @RequestMapping(value = "/trips", method = RequestMethod.POST)
    public void saveTrip(@RequestBody Trip tripResponse) {
        tripResponseService.save(tripResponse);
    }
 }
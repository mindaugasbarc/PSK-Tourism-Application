package com.tourism.psk.controller;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @RequestMapping(value = "groupTrip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTrip(@RequestBody final GroupTrip groupTrip) {

    }

    @RequestMapping(value = "find/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trip> findAllTrips() {
        return tripService.findAll();
    }

    @RequestMapping(value = "find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Trip findTrip(@PathVariable("id") long id) {
        return tripService.find(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Trip trip) {
        tripService.save(trip);
    }

}

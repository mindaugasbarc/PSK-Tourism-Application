package com.tourism.psk.controller;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trip")
public class TripController {

    private final TripService tripService;
    private final SessionService sessionService;

    @Autowired
    public TripController(TripService tripService, SessionService sessionService) {
        this.tripService = tripService;
        this.sessionService = sessionService;
    }


    @RequestMapping(value = "/groupTrip", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTrip(@RequestHeader("Authorization") String authToken, @RequestBody final GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        tripService.addGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/addGroupTrip", method = RequestMethod.POST,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTripRequest(@RequestHeader("Authorization") String authToken,
                                    @RequestBody final GroupTripRequest groupTripRequest) {
        sessionService.authenticate(authToken);
        tripService.addGroupTripThroughRequest(groupTripRequest);
    }

    @RequestMapping(value = "/groupTrips", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupTrip> findGroupTrips(@RequestHeader("Authorization") String authToken) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrips();
    }

    @RequestMapping(value = "/groupTrip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupTrip findGroupTrip(@RequestHeader("Authorization") String authToken,@RequestParam("id") long groupTripId) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrip(groupTripId);
    }
    @RequestMapping(value = "/find/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trip> findAllTrips() {
        return tripService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Trip findTrip(@RequestHeader("Authorization") String authToken, @PathVariable("id") long id) {
        sessionService.authenticate(authToken);
        return tripService.find(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestHeader("Authorization") String authToken, @RequestBody Trip trip) {
        sessionService.authenticate(authToken);
        tripService.save(trip);
    }

}

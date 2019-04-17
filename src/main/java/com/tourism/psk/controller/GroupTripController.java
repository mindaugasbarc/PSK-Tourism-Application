package com.tourism.psk.controller;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupTrip")
public class GroupTripController {

    private final TripService tripService;
    private final SessionService sessionService;

    @Autowired
    public GroupTripController(TripService tripService, SessionService sessionService) {
        this.tripService = tripService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/groupTrip", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void addGroupTrip(@RequestHeader("Authorization") String authToken, @RequestBody final GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        tripService.addGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/addGroupTrip", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void addGroupTripRequest(@RequestHeader("Authorization") String authToken,
                                    @RequestBody final GroupTripRequest groupTripRequest) {
        sessionService.authenticate(authToken);
        tripService.addGroupTripThroughRequest(groupTripRequest);
    }

    @RequestMapping(value = "/groupTrips", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    public List<GroupTrip> findGroupTrips(@RequestHeader("Authorization") String authToken) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrips();
    }

    @RequestMapping(value = "/groupTrip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public GroupTrip findGroupTrip(@RequestHeader("Authorization") String authToken,@RequestParam("id") long groupTripId) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrip(groupTripId);
    }


}

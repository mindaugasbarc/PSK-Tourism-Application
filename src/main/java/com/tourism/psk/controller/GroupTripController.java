package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import com.tourism.psk.validator.GroupTripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class GroupTripController {

    private final TripService tripService;
    private final SessionService sessionService;
    private final GroupTripValidator groupTripValidator;

    @Autowired
    public GroupTripController(TripService tripService, SessionService sessionService, GroupTripValidator groupTripValidator) {
        this.tripService = tripService;
        this.sessionService = sessionService;
        this.groupTripValidator = groupTripValidator;
    }

    @RequestMapping(value = "/group-trip", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @RequestBody final GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        groupTripValidator.validateGroupTrip(groupTrip);
        tripService.addGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/addGroupTrip", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTripRequest(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                                    @RequestBody final GroupTripRequest groupTripRequest) {
        sessionService.authenticate(authToken);
        tripService.addGroupTripThroughRequest(groupTripRequest);
    }

    @RequestMapping(value = "/group-trip", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupTrip> findGroupTrips(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrips();
    }

    @RequestMapping(value = "/group-trip/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupTrip findGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @PathVariable("id") long groupTripId) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrip(groupTripId);
    }

    @RequestMapping(value = "/group-trip", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                                @RequestBody GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        tripService.addGroupTrip(groupTrip);
    }
}

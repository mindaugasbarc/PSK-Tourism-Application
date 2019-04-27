package com.tourism.psk.controller;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GroupTripController {

    private final TripService tripService;
    private final SessionService sessionService;

    @Value("${auth-header-name}")
    private String authHeaderName;

    @Autowired
    public GroupTripController(TripService tripService, SessionService sessionService) {
        this.tripService = tripService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/groupTrip", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTrip(HttpServletRequest request, @RequestBody final GroupTrip groupTrip) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        tripService.addGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/addGroupTrip", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addGroupTripRequest(HttpServletRequest request,
                                    @RequestBody final GroupTripRequest groupTripRequest) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        tripService.addGroupTripThroughRequest(groupTripRequest);
    }

    @RequestMapping(value = "/groupTrips", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupTrip> findGroupTrips(HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return tripService.findGroupTrips();
    }

    @RequestMapping(value = "/groupTrip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupTrip findGroupTrip(HttpServletRequest request,@RequestParam("id") long groupTripId) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return tripService.findGroupTrip(groupTripId);
    }
}

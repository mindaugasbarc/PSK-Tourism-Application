package com.tourism.psk.controller;

import com.tourism.psk.model.Trip;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/trip")
public class TripController {

    private final TripService tripService;
    private final SessionService sessionService;

    @Value("${auth-header-name}")
    private String authHeaderName;

    @Autowired
    public TripController(TripService tripService, SessionService sessionService) {
        this.tripService = tripService;
        this.sessionService = sessionService;
    }


    @RequestMapping(value = "/find/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Trip> findAllTrips(HttpServletRequest request) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return tripService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Trip findTrip(HttpServletRequest request, @PathVariable("id") long id) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        return tripService.find(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void save(HttpServletRequest request, @RequestBody Trip trip) {
        sessionService.authenticate(request.getHeader(authHeaderName));
        tripService.save(trip);
    }

}

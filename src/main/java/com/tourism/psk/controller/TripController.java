package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.Document;
import com.tourism.psk.model.Session;
import com.tourism.psk.model.Trip;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/trip")
public class TripController {

    private final TripService tripService;
    private final SessionService sessionService;

    @Autowired
    public TripController(TripService tripService, SessionService sessionService) {
        this.tripService = tripService;
        this.sessionService = sessionService;
    }


    @RequestMapping(value = "/find/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trip> findAllTrips(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return tripService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Trip findTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @PathVariable("id") long id) {
        sessionService.authenticate(authToken);
        return tripService.find(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @RequestBody Trip trip) {
        sessionService.authenticate(authToken);
        tripService.save(trip);
    }

    @RequestMapping(value = "/{tripId}/document/{documentId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Document getDocument(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                                @PathParam("tripId") final long tripId,
                                @PathParam("documentId") final long documentId) {
        Session session = sessionService.authenticate(authToken);
       return tripService.findTripDocument(session.getUser(), tripId, documentId);
    }

    @RequestMapping(value = "/{tripId}/document/{documentId}", method =  RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateDocument(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                               @PathParam("tripId") final long tripId,
                               @PathParam("documentId") final long documentId,
                               @RequestBody Document updatedDocument) {

        Session session = sessionService.authenticate(authToken);
        tripService.updateTripDocument(session.getUser(), tripId, documentId, updatedDocument);
    }

}

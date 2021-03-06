package com.tourism.psk.controller;

import com.tourism.psk.constants.Globals;
import com.tourism.psk.model.Comment;
import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Session;
import com.tourism.psk.model.Trip;
import com.tourism.psk.service.SessionService;
import com.tourism.psk.service.TripService;
import com.tourism.psk.validator.GroupTripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code = HttpStatus.CREATED)
    public GroupTrip addGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @RequestBody final GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        groupTripValidator.validateGroupTrip(groupTrip);
        return tripService.addGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/group-trip", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public GroupTrip updateGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                                @RequestBody GroupTrip groupTrip) {
        sessionService.authenticate(authToken);
        return tripService.updateGroupTrip(groupTrip);
    }

    @RequestMapping(value = "/group-trip", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupTrip> findGroupTrips(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return tripService.findGroupTripsForUser(session.getUser());
    }

    @RequestMapping(value = "/group-trip/organised", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupTrip> findOrganisedGroupTrips(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return tripService.findOrganisedGroupTripsForUser(session.getUser());
    }

    @RequestMapping(value = "/group-trip/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupTrip findGroupTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken, @PathVariable("id") long groupTripId) {
        sessionService.authenticate(authToken);
        return tripService.findGroupTrip(groupTripId);
    }

    @RequestMapping(value = "/group-trip/{id}/comment", method = RequestMethod.POST)
    public Comment addGroupTripComment(@PathVariable("id") long id,
                                    @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                                    @RequestBody Comment comment) {
        sessionService.authenticate(authToken);
        return tripService.addComment(id, comment);
    }

    @RequestMapping(value = "/trip/{id}:confirm", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Trip confirmTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                            @PathVariable(value = "id") Long tripId) {
        Session session = sessionService.authenticate(authToken);
        return tripService.confirmTrip(session.getUser(), tripId);
    }

    @RequestMapping(value = "/trip/{id}:cancel", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Trip cancelTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                            @PathVariable(value = "id") Long tripId) {
        Session session = sessionService.authenticate(authToken);
        return tripService.changeTripStatus(session.getUser(), tripId, true);
    }

    @RequestMapping(value = "/trip/{id}:restore", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Trip restoreTrip(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken,
                           @PathVariable(value = "id") Long tripId) {
        Session session = sessionService.authenticate(authToken);
        return tripService.changeTripStatus(session.getUser(), tripId, false);
    }

    @RequestMapping(value = "/group-trip/{id}:force", method = RequestMethod.PUT)
    public GroupTrip forceUpdate(@PathVariable("id") long id, @RequestBody GroupTrip groupTrip,
                                 @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        sessionService.authenticate(authToken);
        return tripService.forceUpdate(id, groupTrip);
    }

    @RequestMapping(value = "/group-trip/{id}:approve", method = RequestMethod.PUT)
    public GroupTrip approveGroupTrip(@PathVariable("id") long id,
                                 @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return tripService.approveGroupTrip(id, session.getUser());
    }

    @RequestMapping(value = "/group-trip/{tripOneId}/join/{tripTwoId}", method = RequestMethod.PUT)
    public GroupTrip joinGroupTrips(@PathVariable("tripOneId") long tripOneId,
                                    @PathVariable("tripTwoId") long tripTwoId,
                                    @RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return  tripService.joinTrips(tripOneId, tripTwoId, session.getUser());
    }

    @RequestMapping(value = "/group-trip/all", method = RequestMethod.GET)
    public List<GroupTrip> getAllGroupTrips(@RequestHeader(Globals.ACCESS_TOKEN_HEADER_NAME) String authToken) {
        Session session = sessionService.authenticate(authToken);
        return tripService.findGroupTrips(session.getUser());
    }
}

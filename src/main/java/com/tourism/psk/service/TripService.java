package com.tourism.psk.service;

import com.tourism.psk.model.Document;
import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.model.User;
import com.tourism.psk.model.request.GroupTripRequest;

import java.util.List;

public interface TripService {

    List<Trip> findAll();

    Trip find(long id);

    void save(Trip tripResponse);

    GroupTrip addGroupTrip(GroupTrip groupTrip);

    void addGroupTripThroughRequest(GroupTripRequest groupTripRequest);

    List<GroupTrip> findGroupTrips();

    List<GroupTrip> findGroupTripsForUser(User user);

    GroupTrip findGroupTrip(long id);

    Document findTripDocument(User user, long tripId, long documentId);

    void updateTripDocument(User user, long tripId, long documentId, Document updatedDocument);
}

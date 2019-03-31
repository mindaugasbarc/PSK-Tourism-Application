package com.tourism.psk.service;

import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;

import java.util.List;

public interface TripService {

    List<Trip> findAll();

    Trip find(long id);

    void save(Trip tripResponse);

    void addGroupTrip(GroupTrip groupTrip);
}

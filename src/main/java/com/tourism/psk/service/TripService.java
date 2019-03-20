package com.tourism.psk.service;

import com.tourism.psk.model.Trip;

import java.util.List;

public interface TripService {

    List<Trip> findAll();
    Trip find(long id);
    void save(Trip trip);
}

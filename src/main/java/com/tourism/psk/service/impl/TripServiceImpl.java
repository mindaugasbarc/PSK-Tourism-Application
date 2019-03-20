package com.tourism.psk.service.impl;

import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.Trip;
import com.tourism.psk.repository.TripRepository;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip find(long id) {
        return tripRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }

    @Override
    public void save(Trip trip) {
        tripRepository.save(trip);
    }
}

package com.tourism.psk.service.impl;

import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.response.Trip;
import com.tourism.psk.repository.TripResponseRepository;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripResponseServiceImpl implements TripService {

    private final TripResponseRepository tripResponseRepository;

    @Autowired
    public TripResponseServiceImpl(TripResponseRepository tripResponseRepository) {
        this.tripResponseRepository = tripResponseRepository;
    }

    @Override
    public List<Trip> findAll() {
        return tripResponseRepository.findAll();
    }

    @Override
    public Trip find(final long id) {
        return tripResponseRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }

    @Override
    public void save(Trip tripResponse) {
        tripResponseRepository.save(tripResponse);
    }
}

package com.tourism.psk.service.impl;

import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Trip;
import com.tourism.psk.repository.GroupTripRepository;
import com.tourism.psk.repository.TripRepository;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripResponseRepository;
    private final GroupTripRepository groupTripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripResponseRepository, GroupTripRepository groupTripRepository) {
        this.tripResponseRepository = tripResponseRepository;
        this.groupTripRepository = groupTripRepository;
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

    @Override
    public void addGroupTrip(GroupTrip groupTrip) {
        groupTripRepository.save(groupTrip);
    }
}

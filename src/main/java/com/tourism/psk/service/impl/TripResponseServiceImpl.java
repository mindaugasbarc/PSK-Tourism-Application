package com.tourism.psk.service.impl;

import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.response.TripResponse;
import com.tourism.psk.repository.TripResponseRepository;
import com.tourism.psk.service.TripResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripResponseServiceImpl implements TripResponseService {

    private final TripResponseRepository tripResponseRepository;

    @Autowired
    public TripResponseServiceImpl(TripResponseRepository tripResponseRepository) {
        this.tripResponseRepository = tripResponseRepository;
    }

    @Override
    public List<TripResponse> findAll() {
        return tripResponseRepository.findAll();
    }

    @Override
    public TripResponse find(final long id) {
        return tripResponseRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }
}

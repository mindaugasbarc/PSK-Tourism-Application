package com.tourism.psk.service;

import com.tourism.psk.model.response.TripResponse;

import java.util.List;

public interface TripResponseService {

    List<TripResponse> findAll();

    TripResponse find(long id);
}

package com.tourism.psk.repository;

import com.tourism.psk.model.response.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripResponseRepository extends JpaRepository<Trip, Long> {
}

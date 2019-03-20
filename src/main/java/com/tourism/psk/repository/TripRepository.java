package com.tourism.psk.repository;

import com.tourism.psk.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}

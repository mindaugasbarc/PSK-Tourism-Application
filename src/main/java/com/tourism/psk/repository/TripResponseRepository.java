package com.tourism.psk.repository;

import com.tourism.psk.model.response.TripResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripResponseRepository extends JpaRepository<TripResponse, Long> {
}

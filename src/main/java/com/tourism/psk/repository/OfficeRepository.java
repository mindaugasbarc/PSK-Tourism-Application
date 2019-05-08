package com.tourism.psk.repository;

import com.tourism.psk.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    Optional<Office> findByName(String name);
}

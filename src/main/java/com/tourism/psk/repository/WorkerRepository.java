package com.tourism.psk.repository;

import com.tourism.psk.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}

package com.tourism.psk.service;

import com.tourism.psk.model.Worker;
import com.tourism.psk.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface WorkerService {

    List<Worker> findAll();
    Worker findById(long id);
    void saveWorker(Worker worker);
}

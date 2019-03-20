package com.tourism.psk.service.impl;

import com.tourism.psk.exception.WorkerNotFoundException;
import com.tourism.psk.model.Worker;
import com.tourism.psk.repository.WorkerRepository;
import com.tourism.psk.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    @Override
    public Worker findById(long id) {
        return workerRepository.findById(id).orElseThrow(WorkerNotFoundException::new);
    }

    @Override
    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }
}

package com.tourism.psk.controller;

import com.tourism.psk.model.Worker;
import com.tourism.psk.service.WorkerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @RequestMapping(value = "/find/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Worker> findAll() {
        return workerService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Worker find(@PathVariable("id") long id) {
        return workerService.findById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Worker worker) {
        workerService.saveWorker(worker);
    }


}

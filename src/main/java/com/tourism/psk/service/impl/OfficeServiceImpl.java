package com.tourism.psk.service.impl;

import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.model.Office;
import com.tourism.psk.repository.OfficeRepository;
import com.tourism.psk.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {
    private OfficeRepository officeRepository;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public List<Office> findAll() {
        return officeRepository.findAll();
    }

    @Override
    public Office find(long id) {
        return officeRepository.findById(id).orElseThrow(OfficeNotFoundException::new);
    }
}

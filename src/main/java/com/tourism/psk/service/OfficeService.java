package com.tourism.psk.service;

import com.tourism.psk.model.Office;

import java.util.List;

public interface OfficeService {
    List<Office> findAll();
    Office find(long id);
    Office save(Office office);
}

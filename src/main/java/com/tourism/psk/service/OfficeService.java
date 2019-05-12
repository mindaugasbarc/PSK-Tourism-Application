package com.tourism.psk.service;

import com.tourism.psk.model.Office;
import com.tourism.psk.model.OfficeRoom;

import java.util.Date;
import java.util.List;

public interface OfficeService {
    List<Office> findAll();
    Office find(long id);
    Office save(Office office);
    OfficeRoom getAvailableRooms(long officeId, Date start, Date end);
}

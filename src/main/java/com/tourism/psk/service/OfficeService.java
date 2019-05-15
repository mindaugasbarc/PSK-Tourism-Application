package com.tourism.psk.service;

import com.tourism.psk.model.Office;
import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.OfficeRoomOccupation;

import java.util.Date;
import java.util.List;

public interface OfficeService {
    List<Office> findAll();
    Office find(long id);
    Office save(Office office);
    List<OfficeRoom> getAvailableRooms(long officeId, String from, String to);
    List<OfficeRoomOccupation> getOfficeRoomOccupations(long officeId, long roomId, String from, String to);
}

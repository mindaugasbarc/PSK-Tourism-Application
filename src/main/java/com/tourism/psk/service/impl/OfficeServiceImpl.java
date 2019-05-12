package com.tourism.psk.service.impl;

import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.exception.ValueNotProvidedException;
import com.tourism.psk.model.Office;
import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.OfficeRoomOccupation;
import com.tourism.psk.repository.OfficeRepository;
import com.tourism.psk.repository.OfficeRoomRepository;
import com.tourism.psk.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {
    private OfficeRepository officeRepository;

    @Value("${min-office-name-length}")
    private int minOfficeNameLength;

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

    @Override
    public Office save(Office office) {
        if (office.getName().length() < minOfficeNameLength) {
            throw new ValueNotProvidedException("Value for field 'name' must be at least 5 characters");
        }
        if (office.getAddress() == null || office.getAddress().isEmpty()) {
            throw new ValueNotProvidedException("Value for field 'address' must be provided");
        }
        return officeRepository.save(office);
    }

    @Override
    public OfficeRoom getAvailableRooms(long officeId, Date start, Date end) {
        return null;
    }
}

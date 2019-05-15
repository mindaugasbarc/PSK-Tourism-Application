package com.tourism.psk.service.impl;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.tourism.psk.exception.InvalidTimePeriodException;
import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.exception.OfficeRoomNotFoundException;
import com.tourism.psk.exception.ValueNotProvidedException;
import com.tourism.psk.model.Office;
import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.OfficeRoomOccupation;
import com.tourism.psk.repository.OfficeRepository;
import com.tourism.psk.repository.OfficeRoomOccupationRepository;
import com.tourism.psk.repository.OfficeRoomRepository;
import com.tourism.psk.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImpl implements OfficeService {
    private OfficeRepository officeRepository;
    private OfficeRoomRepository officeRoomRepository;
    private OfficeRoomOccupationRepository officeRoomOccupationRepository;

    @Value("${date-format}")
    private String dateFormat;

    @Value("${min-office-name-length}")
    private int minOfficeNameLength;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository,
                             OfficeRoomOccupationRepository officeRoomOccupationRepository,
                             OfficeRoomRepository officeRoomRepository) {
        this.officeRepository = officeRepository;
        this.officeRoomOccupationRepository = officeRoomOccupationRepository;
        this.officeRoomRepository = officeRoomRepository;
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
    public List<OfficeRoom> getAvailableRooms(long officeId, String from, String to) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date start = format.parse(from);
            Date end = format.parse(to);
            if (end.before(start)) {
                throw new InvalidTimePeriodException();
            }
            Optional<Office> office = officeRepository.findById(officeId);
            if (!office.isPresent()) {
                throw new OfficeNotFoundException();
            }
            List<Long> occupiedRoomIds = officeRoomOccupationRepository.getOccupiedRooms(getRoomIds(office.get()), start, end);
            return getUnoccupiedRooms(office.get().getHouseRooms(), occupiedRoomIds);
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Wrong date format. Use YYYY-MM-DD");
        }
    }

    @Override
    public List<OfficeRoomOccupation> getOfficeRoomOccupations(long officeId, long roomId, String from, String to) {
        Optional<Office> office = officeRepository.findById(officeId);
        if (!office.isPresent()) {
            throw new OfficeNotFoundException();
        }
        if (!officeHasRoom(office.get(), roomId)) {
            throw new OfficeRoomNotFoundException();
        }
        try {
            DateFormat format = new SimpleDateFormat(dateFormat);
            Date start = format.parse(from);
            Date end = format.parse(to);
            if (end.before(start)) {
                throw new InvalidTimePeriodException();
            }
            return officeRoomOccupationRepository.getOfficeRoomOccupationsInPeriod(roomId, start, end);
        }
        catch (ParseException exc) {
            throw new IllegalArgumentException("Wrong date format. Use YYYY-MM-DD");
        }
    }

    private List<Long> getRoomIds(Office office) {
        List<Long> result = new ArrayList<>();
        List<OfficeRoom> rooms = office.getHouseRooms();
        for (OfficeRoom room : rooms) {
            result.add(room.getId());
        }
        return result;
    }

    private List<OfficeRoom> getUnoccupiedRooms(List<OfficeRoom> allRooms, List<Long> occupiedRoomIds) {
        List<OfficeRoom> unoccupiedRooms = new ArrayList<>();
        for (OfficeRoom room : allRooms) {
            if (!occupiedRoomIds.contains(room.getId())) {
                unoccupiedRooms.add(room);
            }
        }
        return unoccupiedRooms;
    }

    private boolean officeHasRoom(Office office, long roomId) {
        for (OfficeRoom room : office.getHouseRooms()) {
            if (room.getId() == roomId) {
                return true;
            }
        }
        return false;
    }
}

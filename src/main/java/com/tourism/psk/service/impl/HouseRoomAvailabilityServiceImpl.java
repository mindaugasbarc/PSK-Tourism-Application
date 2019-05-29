package com.tourism.psk.service.impl;

import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.OfficeRoomOccupation;
import com.tourism.psk.model.User;
import com.tourism.psk.repository.OfficeRoomOccupationRepository;
import com.tourism.psk.service.HouseRoomAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HouseRoomAvailabilityServiceImpl implements HouseRoomAvailabilityService {

    private final SimpleDateFormat dateFormat;
    private final OfficeRoomOccupationRepository officeRoomOccupationRepository;

    @Autowired
    public HouseRoomAvailabilityServiceImpl(@Value("${date-format}") SimpleDateFormat dateFormat, OfficeRoomOccupationRepository officeRoomOccupationRepository) {
        this.dateFormat = dateFormat;
        this.officeRoomOccupationRepository = officeRoomOccupationRepository;
    }

    @Override
    public void validateHouseRoomAvailability(OfficeRoom officeRoom, String fromDate, String toDate) {
        try {
            Date fromDateDate = dateFormat.parse(fromDate);
            Date toDateDate = dateFormat.parse(toDate);
            if(officeRoomOccupationRepository.getOfficeRoomOccupationByOfficeRoom(officeRoom.getId()).stream().anyMatch(officeRoomOccupation -> (officeRoomOccupation.getTo().after(fromDateDate) || officeRoomOccupation.getTo().equals(fromDateDate) && officeRoomOccupation.getTo().before(toDateDate) || officeRoomOccupation.getTo().equals(toDateDate))
                    || (officeRoomOccupation.getFrom().before(fromDateDate) || officeRoomOccupation.getFrom().equals(fromDateDate) && officeRoomOccupation.getTo().after(toDateDate) || officeRoomOccupation.getTo().equals(toDateDate)))) {
                throw new RuntimeException(officeRoom.getName()  + " is occupied from " + fromDate + " to " + toDate);
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addHouseRoomAvailabilitiesIfValid(List<OfficeRoom> officeRoomList, User user, String fromDate, String toDate) {
        officeRoomList.stream().forEach(officeRoom -> validateHouseRoomAvailability(officeRoom, fromDate, toDate));
        try {
            for (OfficeRoom officeRoom : officeRoomList) {
                OfficeRoomOccupation officeRoomOccupation = new OfficeRoomOccupation(dateFormat.parse(fromDate), dateFormat.parse(toDate), user, officeRoom);
                officeRoomOccupationRepository.save(officeRoomOccupation);
            }
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }


    }
}
package com.tourism.psk.service.impl;

import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.service.HouseRoomAvailabilityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HouseRoomAvailabilityServiceImpl implements HouseRoomAvailabilityService {

    private final SimpleDateFormat dateFormat;

    public HouseRoomAvailabilityServiceImpl(@Value("${date-format}") SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void validateHouseRoomAvailability(OfficeRoom officeRoom, String fromDate, String toDate) {
        try {
            Date fromDateDate = dateFormat.parse(fromDate);
            Date toDateDate = dateFormat.parse(toDate);
            if(officeRoom.getOccupations().stream().anyMatch(officeRoomOccupation -> officeRoomOccupation.getTo().after(fromDateDate) && officeRoomOccupation.getTo().before(toDateDate)
                    || officeRoomOccupation.getFrom().before(fromDateDate) && officeRoomOccupation.getTo().after(toDateDate
            ))) {
                throw new RuntimeException(officeRoom.getName()  + " is occupied from " + fromDate + " to " + toDate);
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
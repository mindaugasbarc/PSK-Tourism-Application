package com.tourism.psk.service.impl;

import com.tourism.psk.model.*;
import com.tourism.psk.repository.OfficeRoomOccupationRepository;
import com.tourism.psk.service.HouseRoomAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

            if (!officeRoomOccupationRepository.getOfficeRoomOccupationsInPeriod(officeRoom.getId(), fromDateDate, toDateDate).isEmpty()) {
                throw new RuntimeException(officeRoom.getName() + " is occupied from " + fromDate + " to " + toDate);
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
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }


    }

    @Override
    public void removeHouseRoomAvailabilities(List<OfficeRoom> officeRooms, User user, String fromDate, String toDate) {
        try {
            Date fromDateDate = dateFormat.parse(fromDate);
            Date toDateDate = dateFormat.parse(toDate);
            officeRoomOccupationRepository.deleteAll(officeRooms.stream()
                    .map(officeRoom -> officeRoomOccupationRepository.getOfficeRoomOccupationsInPeriod(officeRoom.getId(), fromDateDate, toDateDate))
                    .flatMap(List::stream)
                    .collect(toList()));

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeHouseRoomAvailabilities(GroupTrip groupTrip) {
        groupTrip.getUserTrips().stream().map(Trip::getHouserooms)
                .flatMap(Collection::stream).forEach(officeRoom -> {
            try {
                List<OfficeRoomOccupation> occupations = officeRoomOccupationRepository.getOfficeRoomOccupationsInPeriod(
                        officeRoom.getId(),
                        dateFormat.parse(groupTrip.getDateFrom()),
                        dateFormat.parse(groupTrip.getDateTo())
                );
                if (occupations.size() != 1) {
                    throw new RuntimeException("Multiple occupation on the same date");
                }
                officeRoomOccupationRepository.delete(occupations.get(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }


}
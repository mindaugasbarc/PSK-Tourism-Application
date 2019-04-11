package com.tourism.psk.service.impl;

import com.tourism.psk.constants.TripStatus;
import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.GroupTrip;
import com.tourism.psk.model.Office;
import com.tourism.psk.model.Trip;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.repository.GroupTripRepository;
import com.tourism.psk.repository.OfficeRepository;
import com.tourism.psk.repository.TripRepository;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripResponseRepository;
    private final GroupTripRepository groupTripRepository;
    private final OfficeRepository officeRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripResponseRepository, GroupTripRepository groupTripRepository, OfficeRepository officeRepository) {
        this.tripResponseRepository = tripResponseRepository;
        this.groupTripRepository = groupTripRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    public List<Trip> findAll() {
        return tripResponseRepository.findAll();
    }

    @Override
    public Trip find(final long id) {
        return tripResponseRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }

    @Override
    public void save(Trip tripResponse) {
        tripResponseRepository.save(tripResponse);
    }

    @Override
    public void addGroupTrip(GroupTrip groupTrip) {
        groupTripRepository.save(groupTrip);
    }

    @Override
    public void addGroupTripThroughRequest(GroupTripRequest groupTripRequest) {
        Office officeFrom = officeRepository.findByName(groupTripRequest.getOfficeFromName())
                .orElseThrow(OfficeNotFoundException::new);
        Office officeTo = officeRepository.findByName(groupTripRequest.getOfficeToName())
                .orElseThrow(OfficeNotFoundException::new);
        GroupTrip groupTrip = new GroupTrip(groupTripRequest.getName(), groupTripRequest.getDescription(),
                new HashSet<>(), officeFrom, officeTo, new ArrayList<>(), groupTripRequest.getDateFrom(),
                groupTripRequest.getDateTo(), TripStatus.PENDING);
        groupTripRepository.save(groupTrip);
    }

    @Override
    public List<GroupTrip> findGroupTrips() {
        return groupTripRepository.findAll();
    }

    @Override
    public GroupTrip findGroupTrip(final long id) {
        return groupTripRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }
}

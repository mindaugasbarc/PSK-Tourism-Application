package com.tourism.psk.service.impl;

import com.tourism.psk.constants.TripStatus;
import com.tourism.psk.exception.DocumentNotFoundException;
import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.*;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.repository.GroupTripRepository;
import com.tourism.psk.repository.OfficeRepository;
import com.tourism.psk.repository.TripRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripResponseRepository;
    private final GroupTripRepository groupTripRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripResponseRepository, GroupTripRepository groupTripRepository, OfficeRepository officeRepository, UserRepository userRepository) {
        this.tripResponseRepository = tripResponseRepository;
        this.groupTripRepository = groupTripRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
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
                groupTripRequest.getDateTo(), TripStatus.PENDING, userRepository.findById(groupTripRequest.getUserId()));
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

    @Override
    public Document findTripDocument(final User user, final long tripId, final long documentId) {
        Optional<Trip> tripOptional = user.getTrips().stream().filter(trip ->trip.getId() == tripId ).findFirst();
        if (tripOptional.isPresent()) {
            return tripOptional.get().getDocuments().stream().filter(document -> document.getId() == documentId).findFirst()
                    .orElseThrow(DocumentNotFoundException::new);
        } else {
            throw new TripNotFoundException();
        }
    }

    @Override
    public void updateTripDocument(final User user, final long tripId, final long documentId, final Document updatedDocument) {
        Optional<Trip> tripOptional = user.getTrips().stream().filter(trip -> trip.getId() == tripId).findFirst();
        if (tripOptional.isPresent()) {
            Document doc = findDocumentById(tripOptional.get(), documentId);
            doc = updateDocument(doc, updatedDocument);
            List<Document> documents = getOtherDocuments(documentId, tripOptional.get().getDocuments());
            documents.add(doc);
            Trip trip = tripOptional.get();
            trip.setDocuments(documents);
            tripResponseRepository.save(trip);

        } else {
            throw new TripNotFoundException();
        }
    }

    private List<Document> getOtherDocuments(final long documentId, List<Document> documentsList) {
        return documentsList.stream()
                .filter(document -> document.getId() != documentId)
                .collect(Collectors.toList());
    }

    private Document updateDocument(final Document oldDocument, final Document newDocument) {
        oldDocument.setDocumentStatus(newDocument.getDocumentStatus());
        oldDocument.setDocumentType(newDocument.getDocumentType());
        oldDocument.setJson(newDocument.getJson());
        return oldDocument;
    }

    private Document findDocumentById(final Trip trip, final long documentId) {
        return trip.getDocuments().stream()
                .filter(document -> document.getId() == documentId).findFirst()
                .orElseThrow(DocumentNotFoundException::new);
    }
}

package com.tourism.psk.service.impl;

import com.tourism.psk.constants.TripStatus;
import com.tourism.psk.exception.DocumentNotFoundException;
import com.tourism.psk.exception.OfficeNotFoundException;
import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.*;
import com.tourism.psk.model.request.GroupTripRequest;
import com.tourism.psk.repository.*;
import com.tourism.psk.service.TripService;
import com.tourism.psk.service.UserOccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripResponseRepository;
    private final GroupTripRepository groupTripRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;
    private final UserOccupationService userOccupationService;
    private final CommentRepository commentRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripResponseRepository,
                           GroupTripRepository groupTripRepository,
                           OfficeRepository officeRepository,
                           UserRepository userRepository,
                           UserOccupationService userOccupationService,
                           CommentRepository commentRepository) {
        this.tripResponseRepository = tripResponseRepository;
        this.groupTripRepository = groupTripRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
        this.userOccupationService = userOccupationService;
        this.commentRepository = commentRepository;
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
    public GroupTrip addGroupTrip(GroupTrip groupTrip) {
        GroupTrip result = groupTripRepository.save(groupTrip);
        final String start = groupTrip.getDateFrom();
        final String end = groupTrip.getDateTo();
        groupTrip.getUserTrips().stream().map(Trip::getUser).forEach(user -> {
            userOccupationService.markAvailability(user.getId(), start, end, false);
        });
        groupTrip.getUserTrips().forEach(trip -> {
            trip.setUser(userRepository.findById(trip.getUser().getId()));
        });
        return result;
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
    public List<GroupTrip> findGroupTripsForUser(User user) {
        return groupTripRepository.findAll().stream().filter(groupTrip -> groupTrip.getUserTrips().stream().map(Trip::getUser)
                .anyMatch(user1 -> user1.equals(user)) || groupTrip.getAdvisor().equals(user))
                .collect(toList());
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

    @Override
    public Comment addComment(long groupTripId, Comment comment) {
        Optional<GroupTrip> groupTrip = groupTripRepository.findById(groupTripId);
        if (!groupTrip.isPresent()) {
            throw new TripNotFoundException();
        }
        comment.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        comment.setUser(userRepository.findById(comment.getUser().getId()));
        comment.setGroupTrip(groupTrip.get());
        return commentRepository.save(comment);
    }

    private List<Document> getOtherDocuments(final long documentId, List<Document> documentsList) {
        return documentsList.stream()
                .filter(document -> document.getId() != documentId)
                .collect(toList());
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

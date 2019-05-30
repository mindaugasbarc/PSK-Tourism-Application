package com.tourism.psk.service.impl;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.exception.DocumentNotFoundException;
import com.tourism.psk.exception.EntityModifiedException;
import com.tourism.psk.exception.TripNotFoundException;
import com.tourism.psk.model.*;
import com.tourism.psk.repository.*;
import com.tourism.psk.service.HouseRoomAvailabilityService;
import com.tourism.psk.service.TripService;
import com.tourism.psk.service.UserOccupationService;
import com.tourism.psk.validator.GroupTripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final HouseRoomAvailabilityService houseRoomAvailabilityService;
    private final CommentRepository commentRepository;
    private final OfficeRoomRepository officeRoomRepository;
    private final TripRepository tripRepository;
    private final GroupTripValidator groupTripValidator;

    @Autowired
    public TripServiceImpl(TripRepository tripResponseRepository,
                           GroupTripRepository groupTripRepository,
                           OfficeRepository officeRepository,
                           UserRepository userRepository,
                           UserOccupationService userOccupationService,
                           HouseRoomAvailabilityService houseRoomAvailabilityService,
                           CommentRepository commentRepository,
                           OfficeRoomRepository officeRoomRepository,
                           TripRepository tripRepository, GroupTripValidator groupTripValidator) {
        this.tripResponseRepository = tripResponseRepository;
        this.groupTripRepository = groupTripRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
        this.userOccupationService = userOccupationService;
        this.houseRoomAvailabilityService = houseRoomAvailabilityService;
        this.commentRepository = commentRepository;
        this.officeRoomRepository = officeRoomRepository;
        this.tripRepository = tripRepository;
        this.groupTripValidator = groupTripValidator;
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

        groupTrip.getUserTrips().forEach(trip -> {
            trip.setUser(userRepository.findById(trip.getUser().getId()));
            if (trip.getHouserooms() != null && trip.getHouserooms().size() > 0) {
                trip.getHouserooms().forEach(houseroom -> houseroom = officeRoomRepository.getOne(houseroom.getId()));
            }
        });

        groupTrip.setOfficeFrom(officeRepository.getOne(groupTrip.getOfficeFrom().getId()));
        groupTrip.setOfficeTo(officeRepository.getOne(groupTrip.getOfficeTo().getId()));

        groupTrip.setAdvisor(userRepository.findById(groupTrip.getAdvisor().getId()));

        GroupTrip result = groupTripRepository.save(groupTrip);

        final String start = groupTrip.getDateFrom();
        final String end = groupTrip.getDateTo();
        groupTrip.getUserTrips().stream().map(Trip::getUser).forEach(user -> {
            userOccupationService.markAvailability(user.getId(), start, end, false);
        });

        groupTrip.getUserTrips()
                .forEach(trip ->
                        houseRoomAvailabilityService.addHouseRoomAvailabilitiesIfValid(trip.getHouserooms(), trip.getUser(), groupTrip.getDateFrom(), groupTrip.getDateTo()));

        return result;
    }

    @Override
    @Transactional
    public GroupTrip updateGroupTrip(GroupTrip groupTrip) {
        try {
            GroupTrip oldGroupTrip = groupTripRepository.findById(groupTrip.getId()).orElseThrow(TripNotFoundException::new);
            oldGroupTrip.getUserTrips().forEach(trip -> houseRoomAvailabilityService.removeHouseRoomAvailabilities(trip.getHouserooms(), trip.getUser(), oldGroupTrip.getDateFrom(), oldGroupTrip.getDateTo()));
            oldGroupTrip.getUserTrips().stream().map(Trip::getUser).forEach(user ->
                    userOccupationService.markAvailability(user.getId(), oldGroupTrip.getDateFrom(), oldGroupTrip.getDateTo(), true));
            groupTripValidator.validateGroupTrip(groupTrip);
            addGroupTrip(groupTrip);
            return groupTrip;
        }
        catch (ObjectOptimisticLockingFailureException exc) {
            System.out.println(exc.getClass().getName());
            throw new EntityModifiedException();
        }
    }

    @Override
    public List<GroupTrip> findGroupTrips() {
        return groupTripRepository.findAll();
    }

    @Override
    public List<GroupTrip> findGroupTripsForUser(User user) {
        return groupTripRepository.findAll().stream().filter(groupTrip -> groupTrip.getUserTrips().stream().map(Trip::getUser)
                .anyMatch(user1 -> user1.equals(user)))
                .collect(toList());
    }

    @Override
    public List<GroupTrip> findOrganisedGroupTripsForUser(User user) {
        return user.getRole() == UserRole.DEFAULT
                ? new ArrayList<>()
                : groupTripRepository.findAll().stream().filter(groupTrip -> groupTrip.getAdvisor().equals(user)).collect(toList());
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
        comment.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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

    @Override
    public Trip confirmTrip(User user, Long tripId) {
        Trip trip = tripRepository.getOne(tripId);
        if (trip.getUser().getId() == user.getId()) {
            trip.setConfirmed(true);
            return tripRepository.save(trip);
        } else return null;
    }

    @Override
    public Trip changeTripStatus(User user, Long tripId, boolean cancel) {
        Trip trip = tripRepository.getOne(tripId);
        if (trip.getUser().getId() == user.getId()) {
            trip.setRequestedCancel(cancel);
            if (cancel) trip.setConfirmed(false);
            return tripRepository.save(trip);
        } else return null;
    }

    @Override
    @Transactional
    public GroupTrip forceUpdate(long id, GroupTrip groupTrip) {
        Optional<GroupTrip> existingOptional = groupTripRepository.findById(groupTrip.getId());
        if (!existingOptional.isPresent()) {
            throw new TripNotFoundException();
        }
        GroupTrip existing = existingOptional.get();
        existing.getUserTrips().forEach(trip -> {
            userOccupationService.markAvailability(trip.getUser().getId(), existing.getDateFrom(), existing.getDateTo(), true);
        });
        houseRoomAvailabilityService.removeHouseRoomAvailabilities(existing);
        groupTripValidator.validateGroupTrip(groupTrip);
        groupTripRepository.delete(existing);
        return addGroupTrip(groupTrip);
    }
}

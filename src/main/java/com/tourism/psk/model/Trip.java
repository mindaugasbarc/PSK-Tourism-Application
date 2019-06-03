package com.tourism.psk.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;

    @ManyToMany
    private List<OfficeRoom> houserooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Boolean confirmed;

    @NotNull
    private Boolean requestedCancel;

    private Boolean transportBooked;
    private Boolean carRentBooked;

    public long getId() {
        return id;
    }

    public Trip() {
    }

    public Trip(List<Document> documents, List<OfficeRoom> houserooms) {
        this.documents = documents;
        this.houserooms = houserooms;
    }

    public Trip(List<Document> documents, List<OfficeRoom> houserooms, User user, Boolean confirmed, Boolean requestedCancel) {
        this.documents = documents;
        this.houserooms = houserooms;
        this.user = user;
        this.confirmed = confirmed;
        this.requestedCancel = requestedCancel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<OfficeRoom> getHouserooms() {
        return houserooms;
    }

    public void setHouserooms(List<OfficeRoom> houserooms) {
        this.houserooms = houserooms;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getRequestedCancel() {
        return requestedCancel;
    }

    public void setRequestedCancel(Boolean requestedCancel) {
        this.requestedCancel = requestedCancel;
    }

    public Boolean getTransportBooked() {
        return transportBooked;
    }

    public void setTransportBooked(Boolean transportBooked) {
        this.transportBooked = transportBooked;
    }

    public Boolean getCarRentBooked() {
        return carRentBooked;
    }

    public void setCarRentBooked(Boolean carRentBooked) {
        this.carRentBooked = carRentBooked;
    }
}

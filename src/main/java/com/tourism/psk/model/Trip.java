package com.tourism.psk.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Accommodation> houseRooms;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tripInfo_id")
    private TripInfo tripInfo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public Trip() {
    }

    public Trip(List<Document> documents, List<Accommodation> houseRooms, TripInfo tripInfo) {
        this.documents = documents;
        this.houseRooms = houseRooms;
        this.tripInfo = tripInfo;
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

    public List<Accommodation> getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(List<Accommodation> houseRooms) {
        this.houseRooms = houseRooms;
    }

    public TripInfo getTripInfo() {
        return tripInfo;
    }

    public void setTripInfo(TripInfo tripInfo) {
        this.tripInfo = tripInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public Trip() {
    }

    public Trip(List<Document> documents, List<Accommodation> houseRooms) {
        this.documents = documents;
        this.houseRooms = houseRooms;
    }

    public Trip(List<Document> documents, List<Accommodation> houseRooms, User user) {
        this.documents = documents;
        this.houseRooms = houseRooms;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

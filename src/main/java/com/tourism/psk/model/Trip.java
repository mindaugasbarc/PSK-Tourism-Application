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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficeRoom> houserooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public Trip() {
    }

    public Trip(List<Document> documents, List<OfficeRoom> houserooms) {
        this.documents = documents;
        this.houserooms = houserooms;
    }

    public Trip(List<Document> documents, List<OfficeRoom> houserooms, User user) {
        this.documents = documents;
        this.houserooms = houserooms;
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
}

package com.tourism.psk.model;

import com.tourism.psk.model.Accomodation;
import com.tourism.psk.model.Document;
import com.tourism.psk.model.TripInfo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private long id;
    @JoinTable
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;
    @JoinTable
    @OneToMany(cascade = CascadeType.ALL)
    private List<Accomodation> houseRooms;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private TripInfo tripInfo;



    public long getId() {
        return id;
    }

    public Trip() {
    }

    public Trip(List<Document> documents, List<Accomodation> houseRooms, TripInfo tripInfo) {
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

    public List<Accomodation> getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(List<Accomodation> houseRooms) {
        this.houseRooms = houseRooms;
    }

    public TripInfo getTripInfo() {
        return tripInfo;
    }

    public void setTripInfo(TripInfo tripInfo) {
        this.tripInfo = tripInfo;
    }
}

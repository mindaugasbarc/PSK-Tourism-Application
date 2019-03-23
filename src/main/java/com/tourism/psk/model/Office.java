package com.tourism.psk.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Office {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String address;
    @JoinTable
    @OneToMany(cascade = CascadeType.ALL)
    private List<Accomodation> houseRooms;

    public Office() {
    }

    public Office(String name, String address, List<Accomodation> houseRooms) {
        this.name = name;
        this.address = address;
        this.houseRooms = houseRooms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Accomodation> getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(List<Accomodation> houseRooms) {
        this.houseRooms = houseRooms;
    }
}

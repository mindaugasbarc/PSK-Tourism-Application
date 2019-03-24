package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OfficeRoom> houseRooms = Collections.emptyList();

    public Office() {
    }

    public Office(String name, String address, List<OfficeRoom> houseRooms) {
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

    public List<OfficeRoom> getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(List<OfficeRoom> houseRooms) {
        this.houseRooms = houseRooms;
    }

    public void addHouseRoom(OfficeRoom officeRoom) {
        getHouseRooms().add(officeRoom);
        officeRoom.setOffice(this);
    }

    public void removeHouseRoom(OfficeRoom officeRoom) {
        getHouseRooms().remove(officeRoom);
        officeRoom.setOffice(null);
    }
}

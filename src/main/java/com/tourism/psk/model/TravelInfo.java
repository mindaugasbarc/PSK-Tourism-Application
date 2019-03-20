package com.tourism.psk.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class TravelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final String fromLocation;
    private final String toLocation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
    private final List<Transportation> transportation;

    public TravelInfo(String from, String to, List<Transportation> transportation) {
        this.fromLocation = from;
        this.toLocation = to;
        this.transportation = transportation;
    }

    public String startLocation() {
        return fromLocation;
    }

    public String endLocation() {
        return toLocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

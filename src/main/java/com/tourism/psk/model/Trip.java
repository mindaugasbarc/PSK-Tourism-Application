package com.tourism.psk.model;

import javax.persistence.*;
import java.util.List;

@Entity
public final class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private final Duration duration;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private final List<Worker> peopleInTrip;
    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private final TravelInfo travelInfo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
    private final List<Accomodation> accomodation;

    public Trip(Duration duration, List<Worker> peopleInTrip, TravelInfo travelInfo, List<Accomodation> accomodation) {
        this.duration = duration;
        this.peopleInTrip = peopleInTrip;
        this.travelInfo = travelInfo;
        this.accomodation = accomodation;
    }

    public String travelInformation() {
        return "travel Information";
    }


}

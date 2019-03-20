package com.tourism.psk.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class HotelWithPeople extends Accomodation {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Accomodation accomodation;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private List<Worker> peopleInHotel;

    public HotelWithPeople(Accomodation hotel, List<Worker> peopleInHotel) {
        this.accomodation = hotel;
        this.peopleInHotel = peopleInHotel;
    }

    @Override
    public String accomodationInformation() {
        return accomodation.accomodationInformation() + " people who will be staying there:" + peopleInHotel.toString();
    }
}

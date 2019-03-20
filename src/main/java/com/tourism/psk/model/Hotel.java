package com.tourism.psk.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public final class Hotel extends Accomodation {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Duration duration;
    private final String address;

    public Hotel(Duration duration, String address) {
        this.duration = duration;
        this.address = address;
    }

    @Override
    public final String accomodationInformation() {
        return "Hotel information:";
    }


}

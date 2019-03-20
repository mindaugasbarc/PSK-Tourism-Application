package com.tourism.psk.model;

import javax.persistence.*;

@Entity
public abstract class Accomodation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    abstract String accomodationInformation();
}

package com.tourism.psk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    abstract String transportationInfo();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

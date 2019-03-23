package com.tourism.psk.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TripInfo {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Office officeFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Office officeTo;
    private LocalDate fromDate;
    private LocalDate toDate;

    public TripInfo(Office officeFrom, Office officeTo, LocalDate fromDate, LocalDate toDate) {
        this.officeFrom = officeFrom;
        this.officeTo = officeTo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TripInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Office getOfficeFrom() {
        return officeFrom;
    }

    public void setOfficeFrom(Office officeFrom) {
        this.officeFrom = officeFrom;
    }

    public Office getOfficeTo() {
        return officeTo;
    }

    public void setOfficeTo(Office officeTo) {
        this.officeTo = officeTo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}

package com.tourism.psk.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class GroupTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    @JoinTable
    @ManyToMany
    private Set<Trip> trips;
    @ManyToOne
    @JoinTable(name = "groupTrip_officeFrom")
    private Office officeFrom;
    @ManyToOne
    @JoinTable(name = "groupTrip_officeTo")
    private Office officeTo;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @OneToMany(mappedBy = "commentOfTrip")
    List<Comment> comments;

    public GroupTrip(String name, String description, Set<Trip> trips, Office officeFrom,
                     Office officeTo, List<Comment> comments, LocalDate dateFrom, LocalDate dateTo) {
        this.name = name;
        this.description = description;
        this.trips = trips;
        this.officeFrom = officeFrom;
        this.officeTo = officeTo;
        this.comments = comments;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public GroupTrip() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "GroupTrip{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trips=" + trips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTrip groupTrip = (GroupTrip) o;
        return Objects.equals(name, groupTrip.name) &&
                Objects.equals(description, groupTrip.description) &&
                Objects.equals(trips, groupTrip.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, trips);
    }
}

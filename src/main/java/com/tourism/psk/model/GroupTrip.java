package com.tourism.psk.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class GroupTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    @JoinTable
    @ManyToMany
    private Set<Trip> trips;
    @ManyToOne
    @JoinTable(name = "groupTrip_officeFrom")
    private Office from;
    @ManyToOne
    @JoinTable(name = "groupTrip_officeTo")
    private Office to;

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

    public Office getFrom() {
        return from;
    }

    public void setFrom(Office from) {
        this.from = from;
    }

    public Office getTo() {
        return to;
    }

    public void setTo(Office to) {
        this.to = to;
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

package com.tourism.psk.model;

import com.tourism.psk.constants.TripStatus;

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

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private String name;
    private String description;
    @JoinTable
    @ManyToMany
    private Set<Trip> trips;
    @ManyToOne
    @JoinColumn(name = "officeFrom_id")
    private Office officeFrom;
    @ManyToOne
    @JoinColumn(name = "officeTo_id")
    private Office officeTo;

    private String dateFrom;


    private String dateTo;

    @OneToOne
    private User advisor;

    @OneToMany(mappedBy = "commentOfTrip")
    private List<Comment> comments;

    public GroupTrip(String name, String description, Set<Trip> trips, Office officeFrom,
                     Office officeTo, List<Comment> comments, String dateFrom, String dateTo, TripStatus status, User advisor) {
        this.name = name;
        this.description = description;
        this.trips = trips;
        this.officeFrom = officeFrom;
        this.officeTo = officeTo;
        this.comments = comments;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
        this.advisor = advisor;
    }

    public GroupTrip() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
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

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public User getAdvisor() {
        return advisor;
    }

    public void setAdvisor(User advisor) {
        this.advisor = advisor;
    }

    @Override
    public String toString() {
        return "GroupTrip{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trips=" + trips +
                ", advisor=" + advisor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTrip groupTrip = (GroupTrip) o;
        return Objects.equals(name, groupTrip.name) &&
                Objects.equals(description, groupTrip.description) &&
                Objects.equals(trips, groupTrip.trips) &&
                Objects.equals(advisor, groupTrip.advisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, trips, advisor);
    }
}

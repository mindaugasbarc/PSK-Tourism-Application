package com.tourism.psk.model;

import com.tourism.psk.constants.TripStatus;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class GroupTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TripStatus status;

    //@NotNull
    private String name;
    private String description;
    @JoinTable
    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Trip> userTrips;
    @ManyToOne
    @JoinColumn(name = "officeFrom_id")
    @NotNull
    private Office officeFrom;
    @ManyToOne
    @JoinColumn(name = "officeTo_id")
    @NotNull
    private Office officeTo;

    @NotNull
    private String dateFrom;


    @NotNull
    private String dateTo;

    private String title;

    @OneToOne
    @NotNull
    private User advisor;

    @OneToMany(mappedBy = "groupTrip")
    private List<Comment> comments;

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

    public Set<Trip> getUserTrips() {
        return userTrips;
    }

    public void setUserTrips(Set<Trip> userTrips) {
        this.userTrips = userTrips;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "GroupTrip{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userTrips=" + userTrips +
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
                Objects.equals(userTrips, groupTrip.userTrips) &&
                Objects.equals(advisor, groupTrip.advisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, userTrips, advisor);
    }
}

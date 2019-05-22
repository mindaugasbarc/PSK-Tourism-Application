package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OfficeRoomOccupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_from")
    private Date start;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_to")
    private Date end;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "officeRoom_id")
    @JsonIgnore
    private OfficeRoom officeRoom;

    public OfficeRoomOccupation() {
    }

    public OfficeRoomOccupation(Date start, Date end, User user, OfficeRoom officeRoom) {
        this.start = start;
        this.end = end;
        this.user = user;
        this.officeRoom = officeRoom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFrom() {
        return start;
    }

    public void setFrom(Date from) {
        this.start = from;
    }

    public Date getTo() {
        return end;
    }

    public void setTo(Date to) {
        this.end = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OfficeRoom getOfficeRoom() {
        return officeRoom;
    }

    public void setOfficeRoom(OfficeRoom officeRoom) {
        this.officeRoom = officeRoom;
    }
}

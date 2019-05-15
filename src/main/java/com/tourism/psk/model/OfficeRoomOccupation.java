package com.tourism.psk.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OfficeRoomOccupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_from")
    private Date start;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_to")
    private Date end;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "officeRoom_id")
    private OfficeRoom officeRoom;

    public OfficeRoomOccupation() {
    }

    public OfficeRoomOccupation(Date start, Date end, User user, OfficeRoom officeRoom) {
        this.from = start;
        this.to = end;
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
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
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

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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

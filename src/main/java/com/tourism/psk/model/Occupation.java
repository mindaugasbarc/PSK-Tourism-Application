package com.tourism.psk.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date start;
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Occupation() {
    }

    public Occupation(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
}

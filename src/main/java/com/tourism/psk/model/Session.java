package com.tourism.psk.model;

import com.tourism.psk.constants.SessionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {@Index(name = "access_token", columnList = "token", unique = true)})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;
    private String token;
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    private Date created;
    private Date expires;

    public Session() {
    }

    public Session(String token, SessionStatus status, Date created, Date expires) {
        this.token = token;
        this.status = status;
        this.created = created;
        this.expires = expires;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}

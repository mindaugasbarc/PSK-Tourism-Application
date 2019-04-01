package com.tourism.psk.model;

import com.tourism.psk.constants.UserRole;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    private String password;
    private String fullname;
    private String email;
    @OneToMany
    @JoinColumn
    private List<Trip> trips;
    private UserRole role;

    public User() {
    }

    public User(String username, String password, String fullname, String email, List<Trip> trips, UserRole role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.trips = trips;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

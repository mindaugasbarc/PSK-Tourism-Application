package com.tourism.psk.model.response;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.model.Trip;
import com.tourism.psk.model.User;

import java.util.List;

public class UserResponse {
    private long id;
    private String fullname;
    private String email;
    private UserRole role;
    private List<Trip> trips;

    public UserResponse(User user) {
        this.id = user.getId();
        this.fullname = user.getFullname();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.trips = user.getTrips();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

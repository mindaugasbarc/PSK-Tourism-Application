package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private String text;

    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "groupTrip_id")
    @JsonIgnore
    private GroupTrip groupTrip;

    public Comment() {
    }

    public Comment(User commentedBy, String text, String timestamp, GroupTrip groupTrip) {
        this.user = commentedBy;
        this.text = text;
        this.timestamp = timestamp;
        this.groupTrip = groupTrip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GroupTrip getGroupTrip() {
        return groupTrip;
    }

    public void setGroupTrip(GroupTrip groupTrip) {
        this.groupTrip = groupTrip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                Objects.equals(user, comment.user) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(timestamp, comment.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, text, timestamp);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

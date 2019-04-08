package com.tourism.psk.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private User commentedBy;

    private String text;

    private String date;

    @ManyToOne
    @JoinColumn
    private GroupTrip commentOfTrip;

    public Comment(User commentedBy, String text, String date, GroupTrip commentOfTrip) {
        this.commentedBy = commentedBy;
        this.text = text;
        this.date = date;
        this.commentOfTrip = commentOfTrip;
    }

    public User getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(User commentedBy) {
        this.commentedBy = commentedBy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                Objects.equals(commentedBy, comment.commentedBy) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentedBy, text, date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentedBy=" + commentedBy +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

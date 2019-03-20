package com.tourism.psk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate start;
    private LocalDate end;

    public Duration(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            this.start = start;
            this.end = end;
         } else {
            throw new IllegalArgumentException(String.format("the start date {} should start before the end date {}", start, end));
        }

    }

    private long durationInDays() {
            return DAYS.between(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duration duration = (Duration) o;
        return Objects.equals(start, duration.start) &&
                Objects.equals(end, duration.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Duration{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

package com.software.combinedVolunteerList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VolunteerHistoryViewId implements Serializable {

    private String volunteerId;
    private int eventId;

    // Default constructor
    public VolunteerHistoryViewId() {}

    // Getters and setters
    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    // Override equals and hashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteerHistoryViewId that = (VolunteerHistoryViewId) o;
        return eventId == that.eventId && volunteerId.equals(that.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteerId, eventId);
    }
}
package com.software.eventmanagement.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private String eventDescription;
    private String location;

    @ElementCollection
    private Set<Skill> skills;  // This is where the Enum will be used

    private String urgency;
    private LocalDate eventDate;
    private int slotsFilled;

    // Constructors
    public Event() {}

    public Event(String eventName, String eventDescription, String location, Set<Skill> skills, String urgency, LocalDate eventDate) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.location = location;
        this.skills = skills;
        this.urgency = urgency;
        this.eventDate = eventDate;
        this.slotsFilled = 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getSlotsFilled() {
        return slotsFilled;
    }

    public void setSlotsFilled(int slotsFilled) {
        this.slotsFilled = slotsFilled;
    }
}

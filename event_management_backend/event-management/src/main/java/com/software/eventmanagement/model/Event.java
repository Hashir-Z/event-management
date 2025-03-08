package com.software.eventmanagement.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private String eventDescription;
    private String location;
    private List<String> skills;
    private String urgency;
    private LocalDate eventDate;
    private int slotsFilled;

    // Constructors
    public Event() {}

    public Event(String eventName, String eventDescription, String location, List<String> skills, String urgency, LocalDate eventDate) {
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

    public int getSlotsFilled() {
        return slotsFilled;
    }
    
    public void setSlotsFilled(int slotsFilled) {
        this.slotsFilled = slotsFilled;
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
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
}

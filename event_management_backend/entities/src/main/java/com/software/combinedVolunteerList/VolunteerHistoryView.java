package com.software.combinedVolunteerList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Entity
public class VolunteerHistoryView {

    @Id
    private String volunteerId; // Assuming the volunteer's ID is the primary key
    private String volunteerName;
    private String volunteerLocation;
    private int eventId;
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String requiredSkills;
    private String urgency;
    private String eventDate;
    private String status;
}
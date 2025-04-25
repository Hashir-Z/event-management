package com.software.combinedVolunteerList;


import com.software.volunteer_history.VolunteerHistoryDTO;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "volunteer_history_with_details")
@IdClass(VolunteerHistoryViewId.class)
public class VolunteerHistoryWithDetailsDTO {

    @Id
    @Column(name = "volunteer_id")
    private String volunteerId;

    @Id
    @Column(name = "event_id")
    private int eventId;

    @Column(name = "volunteer_name")
    private String volunteerName;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "required_skills")
    private String requiredSkills;

    @Column(name = "urgency")
    private String urgency;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "participation_status")
    private String status;
}
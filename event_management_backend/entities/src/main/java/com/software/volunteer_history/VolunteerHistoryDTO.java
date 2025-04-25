package com.software.volunteer_history;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class VolunteerHistoryDTO {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private String location;
    private List<String> requiredSkills;
    private String urgency;
    private LocalDateTime eventDate;
    private String status;
}

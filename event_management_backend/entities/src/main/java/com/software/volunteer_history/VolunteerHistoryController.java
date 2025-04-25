package com.software.volunteer_history;

import com.software.combinedVolunteerList.VolunteerHistoryWithDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VolunteerHistoryController {

    private final VolunteerHistoryService volunteerHistoryService;

    @Autowired
    public VolunteerHistoryController(VolunteerHistoryService volunteerHistoryService) {
        this.volunteerHistoryService = volunteerHistoryService;
    }

    // Endpoint to get volunteer history with details
    @GetMapping("/api/volunteer-history")
    public List<VolunteerHistoryWithDetailsDTO> getVolunteerHistory() {
        return volunteerHistoryService.getVolunteerHistoryWithDetails();
    }
}
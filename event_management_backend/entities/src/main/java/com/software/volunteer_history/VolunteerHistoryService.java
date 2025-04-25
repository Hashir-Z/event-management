package com.software.volunteer_history;

import com.software.combinedVolunteerList.VolunteerHistoryWithDetailsDTO;
import com.software.combinedVolunteerList.VolunteerHistoryWithDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerHistoryService {

    private final VolunteerHistoryWithDetailsRepository volunteerHistoryRepository;

    @Autowired
    public VolunteerHistoryService(VolunteerHistoryWithDetailsRepository volunteerHistoryRepository) {
        this.volunteerHistoryRepository = volunteerHistoryRepository;
    }

    // Fetch volunteer history from the view
    public List<VolunteerHistoryWithDetailsDTO> getVolunteerHistoryWithDetails() {
        return volunteerHistoryRepository.findAll();
    }
}
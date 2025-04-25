package com.software.volunteer;
import com.software.volunteer_history.VolunteerHistoryDTO;
import com.software.volunteer_history.VolunteerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;



@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable String id) {
        Volunteer volunteer = volunteerService.getVolunteerById(id);
        return volunteer != null ? ResponseEntity.ok(volunteer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.saveVolunteer(volunteer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable String id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }
}
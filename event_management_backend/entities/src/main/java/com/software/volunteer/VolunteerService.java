package com.software.volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Volunteer getVolunteerById(String id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public void deleteVolunteer(String id) {
        volunteerRepository.deleteById(id);
    }
}
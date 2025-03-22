package com.software.eventmanagement.service;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.model.Skill;
import com.software.eventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class EventFormService {

    @Autowired
    private EventRepository eventRepository;

    // Create a new event with associated skills
    public Event createEventWithSkills(String eventName, String eventDescription, String location,
                                       Set<Skill> skills, String urgency, LocalDate eventDate) {

        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDescription(eventDescription);
        event.setLocation(location);
        event.setSkills(skills);
        event.setUrgency(urgency);
        event.setEventDate(eventDate);
        event.setSlotsFilled(0);

        // Save and return the event
        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Event getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null); // Return null if event is not found
    }

    // Update an existing event
    public Event updateEvent(Long id, String eventName, String eventDescription, String location,
                             Set<Skill> skills, String urgency, LocalDate eventDate) {

        Optional<Event> existingEventOptional = eventRepository.findById(id);

        if (!existingEventOptional.isPresent()) {
            return null; // Event not found
        }

        Event existingEvent = existingEventOptional.get();

        existingEvent.setEventName(eventName);
        existingEvent.setEventDescription(eventDescription);
        existingEvent.setLocation(location);
        existingEvent.setSkills(skills);
        existingEvent.setUrgency(urgency);
        existingEvent.setEventDate(eventDate);

        // Save and return the updated event
        return eventRepository.save(existingEvent);
    }

    // Delete an event by ID
    public boolean deleteEvent(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent()) {
            return false; // Event not found
        }

        eventRepository.deleteById(id);
        return true; // Event successfully deleted
    }
}

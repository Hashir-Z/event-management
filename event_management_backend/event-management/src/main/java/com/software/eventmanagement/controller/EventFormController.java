package com.software.eventmanagement.controller;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.model.Skill;
import com.software.eventmanagement.service.EventFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventFormController {

    private final EventFormService eventService;

    @Autowired
    public EventFormController(EventFormService eventService) {
        this.eventService = eventService;
    }

    // Create Event
    @PostMapping("/add")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        // Call service method to create event
        Event createdEvent = eventService.createEventWithSkills(
                event.getEventName(),
                event.getEventDescription(),
                event.getLocation(),
                event.getSkills(),
                event.getUrgency(),
                event.getEventDate()
        );
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // Update Event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        // Call service method to update event
        Event updatedEvent = eventService.updateEvent(
                id,
                event.getEventName(),
                event.getEventDescription(),
                event.getLocation(),
                event.getSkills(),
                event.getUrgency(),
                event.getEventDate()
        );

        if (updatedEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    // Get Event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Get All Events
    @GetMapping
    public ResponseEntity<Iterable<Event>> getAllEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Delete Event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean isDeleted = eventService.deleteEvent(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.software.eventmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.eventmanagement.exception.ResourceNotFoundException;
import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.repository.EventRepository;
import com.software.eventmanagement.service.EventFormService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/events")
public class EventFormController {

    private final EventFormService eventService;
    private final EventRepository eventRepository;

    // Combine both constructor injections into one
    @Autowired
    public EventFormController(EventFormService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
    Event existingEvent = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

    existingEvent.setEventName(updatedEvent.getEventName());
    existingEvent.setEventDescription(updatedEvent.getEventDescription());
    existingEvent.setLocation(updatedEvent.getLocation());
    existingEvent.setSkills(updatedEvent.getSkills());
    existingEvent.setUrgency(updatedEvent.getUrgency());
    existingEvent.setEventDate(updatedEvent.getEventDate());
    existingEvent.setSlotsFilled(updatedEvent.getSlotsFilled());

    Event updated = eventRepository.save(existingEvent);
    return ResponseEntity.ok(updated);
}

    
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

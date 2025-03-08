package com.software.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.repository.EventRepository;

import jakarta.transaction.Transactional;

@Service
public class EventFormService {

    private final EventRepository eventRepository;

    @Autowired
    public EventFormService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event createEvent(Event event) {
        event.setSlotsFilled(0);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> existingEventOptional = eventRepository.findById(id);

        if (existingEventOptional.isPresent()) {
            Event existingEvent = existingEventOptional.get();

            // Update all fields
            existingEvent.setEventName(updatedEvent.getEventName());
            existingEvent.setEventDescription(updatedEvent.getEventDescription());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setSkills(updatedEvent.getSkills());
            existingEvent.setUrgency(updatedEvent.getUrgency());
            existingEvent.setEventDate(updatedEvent.getEventDate());
            existingEvent.setSlotsFilled(updatedEvent.getSlotsFilled());

            return eventRepository.save(existingEvent); // Save the updated event
        } else {
            return null; // Event not found
        }
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

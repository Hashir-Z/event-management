package com.software.eventmanagement.service;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.model.Skill;
import com.software.eventmanagement.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito in JUnit 5
class EventFormServiceTest {

    @Mock
    private EventRepository eventRepository; // Mock repository

    @InjectMocks
    private EventFormService eventFormService; // Inject mock into service

    private Event event;
    private final Long eventId = 1L;

    @BeforeEach
    void setUp() {
        // Initialize an Event object before each test
        event = new Event("Tech Meetup", "A networking event for developers",
                "Downtown", Set.of(Skill.TECHNICAL, Skill.COMMUNICATION),
                "High", LocalDate.of(2025, 5, 20));
        event.setId(eventId);
    }

    @Test
    void testCreateEventWithSkills() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventFormService.createEventWithSkills(
                "Tech Meetup", "A networking event for developers",
                "Downtown", Set.of(Skill.TECHNICAL, Skill.COMMUNICATION),
                "High", LocalDate.of(2025, 5, 20));

        assertNotNull(savedEvent);
        assertEquals("Tech Meetup", savedEvent.getEventName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<Event> events = eventFormService.getAllEvents();

        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        assertEquals("Tech Meetup", events.get(0).getEventName());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testGetEventById() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event foundEvent = eventFormService.getEventById(eventId);

        assertNotNull(foundEvent);
        assertEquals(eventId, foundEvent.getId());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void testUpdateEvent() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventFormService.updateEvent(eventId,
                "Updated Event", "Updated Description", "Updated Location",
                Set.of(Skill.LEADERSHIP), "Medium", LocalDate.of(2025, 6, 15));

        assertNotNull(updatedEvent);
        assertEquals("Updated Event", updatedEvent.getEventName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testDeleteEvent() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        boolean isDeleted = eventFormService.deleteEvent(eventId);

        assertTrue(isDeleted);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void testDeleteEventNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        boolean isDeleted = eventFormService.deleteEvent(eventId);

        assertFalse(isDeleted);
        verify(eventRepository, never()).deleteById(eventId);
    }
}

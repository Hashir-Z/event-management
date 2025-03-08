package com.software.eventmanagement.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class EventFormServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventFormService eventService;

    private Event testEvent;

    @BeforeEach
    public void setUp() {
        testEvent = new Event("Test Event", "Description", "Location", List.of("Skill1", "Skill2"), "High", LocalDate.now());
    }

    @Test
    public void testCreateEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        Event createdEvent = eventService.createEvent(testEvent);

        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getEventName());
    }

    @Test
    public void testGetAllEvents() {
        List<Event> eventList = List.of(testEvent);
        when(eventRepository.findAll()).thenReturn(eventList);

        List<Event> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetEventById_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        Event event = eventService.getEventById(1L);

        assertNotNull(event);
        assertEquals("Test Event", event.getEventName());
    }

    @Test
    public void testGetEventById_NotFound() {
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        Event event = eventService.getEventById(999L);

        assertNull(event);
    }

    @Test
    public void testUpdateEvent_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        Event updatedEvent = eventService.updateEvent(1L, testEvent);

        assertNotNull(updatedEvent);
        assertEquals("Test Event", updatedEvent.getEventName());
    }

    @Test
    public void testUpdateEvent_NotFound() {
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        Event updatedEvent = eventService.updateEvent(999L, testEvent);

        assertNull(updatedEvent);
    }

    @Test
    public void testDeleteEvent() {
        doNothing().when(eventRepository).deleteById(1L);

        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).deleteById(1L);
    }
}

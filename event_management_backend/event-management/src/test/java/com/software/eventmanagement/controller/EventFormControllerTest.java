package com.software.eventmanagement.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.repository.EventRepository;
import com.software.eventmanagement.service.EventFormService;

@SpringBootTest
@AutoConfigureMockMvc
public class EventFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private EventFormService eventService;

    private Event existingEvent;
    private Event newEvent;

    @BeforeEach
    public void setUp() {
        // Initialize the test events
        existingEvent = new Event("Event 1", "Description", "Location", Arrays.asList("Skill1"), "High", LocalDate.now());
        existingEvent.setId(1L);
        
        newEvent = new Event("Event 2", "New Event Description", "New Location", Arrays.asList("Skill2"), "Medium", LocalDate.of(2025, 5, 15));
        newEvent.setId(2L);
    }

    @Test
public void testCreateEvent() throws Exception {
    Event event = new Event("Test Event", "Description", "Location", List.of("Skill1", "Skill2"), "High", LocalDate.now());

    // Mocking the event creation
    when(eventService.createEvent(any(Event.class))).thenReturn(event);

    // Perform the POST request and check if eventName is returned
    mockMvc.perform(post("/api/events/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"eventName\":\"Test Event\", \"eventDescription\":\"Description\", \"location\":\"Location\", \"skills\":[\"Skill1\", \"Skill2\"], \"urgency\":\"High\", \"eventDate\":\"2025-03-07\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.eventName").value("Test Event"));
}

@Test
public void testUpdateEvent() throws Exception {
    Long eventId = 1L;
    Event existingEvent = new Event("Old Event", "Old Description", "Old Location", List.of("Skill1"), "Low", LocalDate.now());
    Event updatedEvent = new Event("Updated Event", "Updated Description", "Updated Location", List.of("Skill2"), "High", LocalDate.now().plusDays(1));

    // Mocking the findById() to return the existing event
    when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));

    // Mocking the save() method to return the updated event
    when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

    // Perform the PUT request to update the event
    mockMvc.perform(put("/api/events/{id}", eventId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"eventName\":\"Updated Event\", \"eventDescription\":\"Updated Description\", \"location\":\"Updated Location\", \"skills\":[\"Skill2\"], \"urgency\":\"High\", \"eventDate\":\"2025-03-08\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.eventName").value("Updated Event"));
}


    @Test
public void testGetEventById() throws Exception {
    Long eventId = 1L;
    Event event = new Event("Test Event", "Description", "Location", List.of("Skill1", "Skill2"), "High", LocalDate.now());

    // Mocking the getEventById behavior
    when(eventService.getEventById(eventId)).thenReturn(event);

    // Perform GET request and check if eventName is returned
    mockMvc.perform(get("/api/events/{id}", eventId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.eventName").value("Test Event"));
}


    @Test
    public void testGetEventByIdNotFound() throws Exception {
        // Mock the repository to return an empty optional (event not found)
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform the GET request for a non-existing event
        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isNotFound());  // Expect 404 Not Found
    }

    @Test
public void testGetAllEvents() throws Exception {
    Event event = new Event("Test Event", "Description", "Location", List.of("Skill1", "Skill2"), "High", LocalDate.now());

    // Mocking the getAllEvents behavior
    when(eventService.getAllEvents()).thenReturn(List.of(event));

    // Perform GET request and check if eventName is returned in the list
    mockMvc.perform(get("/api/events")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].eventName").value("Test Event"));
}








}

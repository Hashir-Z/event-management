package com.software.eventmanagement.controller;

import com.software.eventmanagement.model.Event;
import com.software.eventmanagement.model.Skill;
import com.software.eventmanagement.service.EventFormService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventFormControllerTest {

    @Mock
    private EventFormService eventService;

    @InjectMocks
    private EventFormController eventController;

    private MockMvc mockMvc;
    private Event event;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        event = new Event(
                "Community Cleanup",
                "A local cleanup event",
                "City Park",
                new HashSet<>(Arrays.asList(Skill.TEAMWORK, Skill.ORGANIZATION)),
                "High",
                LocalDate.of(2025, 5, 15)
        );
        event.setId(1L);
    }

    @Test
    void testGetAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Collections.singletonList(event));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].eventName").value("Community Cleanup"));

        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testGetEventById() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(event);  // FIXED: Remove Optional

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Community Cleanup"));

        verify(eventService, times(1)).getEventById(1L);
    }

    @Test
    void testAddEvent() throws Exception {
        when(eventService.createEventWithSkills(
                anyString(), anyString(), anyString(), anySet(), anyString(), any(LocalDate.class)
        )).thenReturn(event);

        mockMvc.perform(post("/api/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"eventName\": \"Community Cleanup\", " +
                                "\"eventDescription\": \"A local cleanup event\", " +
                                "\"location\": \"City Park\", " +
                                "\"skills\": [\"TEAMWORK\", \"ORGANIZATION\"], " +
                                "\"urgency\": \"High\", " +
                                "\"eventDate\": \"2025-05-15\" }"))
                .andExpect(status().isCreated())  // ✅ Change from isOk() to isCreated()
                .andExpect(jsonPath("$.eventName").value("Community Cleanup"));

        verify(eventService, times(1)).createEventWithSkills(
                anyString(), anyString(), anyString(), anySet(), anyString(), any(LocalDate.class));
    }


    @Test
    void testDeleteEvent() throws Exception {
        when(eventService.deleteEvent(1L)).thenReturn(true);  // FIXED: Return boolean

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());

        verify(eventService, times(1)).deleteEvent(1L);
    }
}

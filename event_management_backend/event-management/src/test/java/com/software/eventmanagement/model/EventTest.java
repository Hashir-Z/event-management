package com.software.eventmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;
    private Set<Skill> skills;

    @BeforeEach
    void setUp() {
        skills = new HashSet<>();
        skills.add(Skill.TEAMWORK);
        skills.add(Skill.ORGANIZATION);

        event = new Event("Community Cleanup", "A local cleanup event", "City Park", skills, "High", LocalDate.of(2025, 5, 15));
    }

    @Test
    void testEventConstructorAndGetters() {
        assertEquals("Community Cleanup", event.getEventName());
        assertEquals("A local cleanup event", event.getEventDescription());
        assertEquals("City Park", event.getLocation());
        assertTrue(event.getSkills().contains(Skill.TEAMWORK));
        assertTrue(event.getSkills().contains(Skill.ORGANIZATION));
        assertEquals("High", event.getUrgency());
        assertEquals(LocalDate.of(2025, 5, 15), event.getEventDate());
    }

    @Test
    void testEventSetters() {
        event.setEventName("New Event");
        event.setEventDescription("Updated description");
        event.setLocation("New Location");
        event.setUrgency("Low");
        event.setEventDate(LocalDate.of(2025, 6, 1));
        event.setSlotsFilled(5);

        assertEquals("New Event", event.getEventName());
        assertEquals("Updated description", event.getEventDescription());
        assertEquals("New Location", event.getLocation());
        assertEquals("Low", event.getUrgency());
        assertEquals(LocalDate.of(2025, 6, 1), event.getEventDate());
        assertEquals(5, event.getSlotsFilled());
    }
}

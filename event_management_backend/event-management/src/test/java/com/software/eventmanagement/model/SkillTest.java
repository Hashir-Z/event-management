package com.software.eventmanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

    @Test
    void testFromString() {
        assertEquals(Skill.COMMUNICATION, Skill.fromString("communication"));
        assertEquals(Skill.LEADERSHIP, Skill.fromString("leadership"));
        assertEquals(Skill.TEAMWORK, Skill.fromString("teamwork"));
        assertEquals(Skill.ORGANIZATION, Skill.fromString("organization"));
        assertEquals(Skill.TECHNICAL, Skill.fromString("technical"));
    }

    @Test
    void testToValue() {
        assertEquals("COMMUNICATION", Skill.COMMUNICATION.toValue());
        assertEquals("LEADERSHIP", Skill.LEADERSHIP.toValue());
        assertEquals("TEAMWORK", Skill.TEAMWORK.toValue());
        assertEquals("ORGANIZATION", Skill.ORGANIZATION.toValue());
        assertEquals("TECHNICAL", Skill.TECHNICAL.toValue());
    }

    @Test
    void testInvalidFromString() {
        // Invalid value should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Skill.fromString("invalid"));
    }
}

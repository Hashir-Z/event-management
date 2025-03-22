package com.software.eventmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillEntityTest {

    private SkillEntity skillEntity;

    @BeforeEach
    void setUp() {
        skillEntity = new SkillEntity(Skill.TEAMWORK);
    }

    @Test
    void testSkillEntityConstructorAndGetter() {
        assertEquals(Skill.TEAMWORK, skillEntity.getName());
    }

    @Test
    void testSetName() {
        skillEntity.setName(Skill.COMMUNICATION);
        assertEquals(Skill.COMMUNICATION, skillEntity.getName());
    }
}

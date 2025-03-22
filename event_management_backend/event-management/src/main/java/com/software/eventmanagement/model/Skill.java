package com.software.eventmanagement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Skill {
    COMMUNICATION,
    LEADERSHIP,
    TEAMWORK,
    ORGANIZATION,
    TECHNICAL;

    @JsonCreator
    public static Skill fromString(String value) {
        return Skill.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name().toUpperCase();
    }
}

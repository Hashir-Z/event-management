package com.software.volunteer_history;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "volunteer_history")
public class VolunteerHistory {

    @EmbeddedId
    private VolunteerHistoryId id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Confirmed;

    public enum Status {
        Confirmed,
        Attended,
        No_show;

        @JsonCreator
        public static Status fromValue(String value) {
            for (Status status : Status.values()) {
                if (status.name().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status: " + value);
        }

        @JsonValue
        public String toValue() {
            return this.name();
        }
    }
}
package com.software.volunteer_history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class VolunteerHistoryId implements Serializable {
    private Integer eventId;
    private String userId;

    public VolunteerHistoryId() {}
    public VolunteerHistoryId(Integer eventId, String userId) {
        this.eventId = eventId;
        this.userId = userId;
    }


}

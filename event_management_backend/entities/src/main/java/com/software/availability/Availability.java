package com.software.availability;

import com.software.volunteer.Volunteer;
import jakarta.persistence.*;

import java.time.LocalTime;


@Entity
@Table(name = "availability")
public class Availability {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    private LocalTime startTime;
    private LocalTime endTime;

    private boolean recurring = true;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    public enum Weekday {
        Mon, Tue, Wed, Thu, Fri, Sat, Sun
    }
}


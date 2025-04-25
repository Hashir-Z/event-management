package com.software.event_details;

import com.software.task.Task;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "event_details")
@Getter
@Setter
public class EventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id")
    private Integer id;
    @Column(name="event_name")
    private String name;
    @Column(name="event_description")
    private String description;
    private String location;

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Task> tasks;

    // Getters and Setters
}

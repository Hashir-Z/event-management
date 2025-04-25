package com.software.assignment;

import com.software.task.Task;
import com.software.volunteer.Volunteer;
import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @Column(name="assigned_at")
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    public enum Status {
        Pending, Confirmed, Declined
    }


}
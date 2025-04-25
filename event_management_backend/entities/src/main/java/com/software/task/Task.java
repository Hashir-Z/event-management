package com.software.task;

import com.software.event.Event;
import com.software.skill.Skill;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;




@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    @Column(name="name")
    private String name;
    @Getter
    @Setter
    @Column(name="description")
    private String description;
    @Getter
    @Setter
    @Column(name="location")
    private String location;

    @Getter
    @Setter
    @Column(name="start_datetime")
    private LocalDateTime startDatetime;
    @Getter
    @Setter
    @Column(name="end_datetime")
    private LocalDateTime endDatetime;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="urgency_level")
    private UrgencyLevel urgencyLevel;
    @Getter
    @Setter
    @Column(name="required_volunteers")
    private Integer requiredVolunteers;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToMany
    @JoinTable(
            name = "task_skills",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    public enum UrgencyLevel {
        Low, Medium, High
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetimeDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Integer getRequiredVolunteers() {
        return requiredVolunteers;
    }

    public void setRequiredVolunteers(Integer requiredVolunteers) {
        this.requiredVolunteers = requiredVolunteers;
    }
}
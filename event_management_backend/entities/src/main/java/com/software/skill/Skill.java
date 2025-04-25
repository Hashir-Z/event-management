package com.software.skill;

import com.software.task.Task;
import com.software.volunteer.Volunteer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<Volunteer> volunteers;

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Task> tasks;

    // Getters and Setters
}

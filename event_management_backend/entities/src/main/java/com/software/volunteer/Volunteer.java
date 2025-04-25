package com.software.volunteer;

import com.software.assignment.Assignment;
import com.software.availability.Availability;
import com.software.preference.Preference;
import com.software.skill.Skill;
import com.software.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;


@Entity
@Table(name = "volunteers")
@Getter
@Setter
public class Volunteer {
    @Id
    private String id; // Same as user_id

    @Column(name="full_name")
    private String name;
    private String location;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "volunteer_skills",
            joinColumns = @JoinColumn(name = "volunteer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )

    private Set<Skill> skills;
    @OneToMany(mappedBy = "volunteer")
    private List<Availability> availability;

    @OneToMany(mappedBy = "volunteer")
    private List<Preference> preferences;

    @OneToMany(mappedBy = "volunteer")
    private List<Assignment> assignments;

    // Getters and Setters

}


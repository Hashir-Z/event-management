package com.software.preference;

import com.software.volunteer.Volunteer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.*;


@Entity
@Table(name = "preferences")
@Getter
@Setter
public class Preference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="preference_type")
    private String preferenceType;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
}
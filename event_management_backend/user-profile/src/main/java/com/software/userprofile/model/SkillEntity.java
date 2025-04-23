package com.software.userprofile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skill")
public class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Skill name;

    public SkillEntity() {}

    public SkillEntity(Skill name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Skill getName() {
        return name;
    }

    public void setName(Skill name) {
        this.name = name;
    }
}

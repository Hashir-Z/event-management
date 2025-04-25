package com.software.userprofile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profile_skills")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private SkillEntity skill;

    public UserProfileSkills(UserProfile userProfile, SkillEntity skill) {
        this.userProfile = userProfile;
        this.skill = skill;
    }
}

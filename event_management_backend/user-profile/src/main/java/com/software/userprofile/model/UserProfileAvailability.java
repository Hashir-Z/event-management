package com.software.userprofile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profile_availability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String availableDate;

    public UserProfileAvailability(UserProfile userProfile, String availableDate) {
        this.userProfile = userProfile;
        this.availableDate = availableDate;
    }
}

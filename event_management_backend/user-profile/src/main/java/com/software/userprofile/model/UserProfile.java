package com.software.userprofile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String preferences;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<UserProfileSkills> skills;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<UserProfileAvailability> availability;

//    @ElementCollection
//    private List<String> skills;

//    @ElementCollection
//    private List<String> availability;
}

package com.software.userprofile.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.software.userprofile.model.UserProfile;

@Service
public class UserProfileService {

    private List<UserProfile> userProfiles;

    public UserProfileService() {
        this.userProfiles = new ArrayList<>(Arrays.asList(
            new UserProfile("John Doe", "123 Main St", "Apt 4", "Houston", "TX", "77001", 
                            Arrays.asList("Java", "React"), "Open to any volunteer work", Arrays.asList("2025-03-01", "2025-03-10")),
            new UserProfile("Jane Smith", "456 Oak Rd", null, "Austin", "TX", "78701", 
                            Arrays.asList("Spring", "MySQL"), "Looking for leadership roles", Arrays.asList("2025-04-01"))
        ));
    }

    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public UserProfile getUserProfile(String fullName) {
        return userProfiles.stream()
                .filter(profile -> profile.getFullName().equalsIgnoreCase(fullName))
                .findFirst()
                .orElse(null);
    }

    public void addUserProfile(UserProfile userProfile) {
        userProfiles.add(userProfile);
    }

    public void clearUserProfiles() {
        userProfiles.clear();
    }

    public UserProfile saveUserProfile(UserProfile userProfile) {
        // add logic to save the user profile in a database
        System.out.println("Saving user profile: " + userProfile);
        return userProfile; // Return the saved profile for now
    }
}

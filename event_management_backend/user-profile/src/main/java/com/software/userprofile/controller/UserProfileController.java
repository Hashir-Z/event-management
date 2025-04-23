package com.software.userprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.software.userprofile.model.UserProfile;
import com.software.userprofile.model.SkillEntity;
import com.software.userprofile.service.UserProfileService;

import java.util.List;

@RestController
@RequestMapping("api/user-profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/all")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Application is running!";
    }

    @GetMapping("/{fullName}")
    public UserProfile getUserProfile(@PathVariable String fullName) {
        UserProfile userProfile = userProfileService.getUserProfile(fullName);
        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }
        return userProfile;
    }

    @PostMapping("/add")
    public ResponseEntity<UserProfile> addUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile savedProfile = userProfileService.saveUserProfile(userProfile);
        return ResponseEntity.ok(savedProfile);
    }

    // Add skills to a user profile
    @PutMapping("/{userProfileId}/skills")
    public ResponseEntity<UserProfile> addSkillsToUserProfile(@PathVariable String userProfileId, @RequestBody List<SkillEntity> skills) {
        userProfileService.addSkillsToUserProfile(userProfileId, skills);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Add availability to a user profile
    @PutMapping("/{userProfileId}/availability")
    public ResponseEntity<UserProfile> addAvailabilityToUserProfile(@PathVariable String userProfileId, @RequestBody List<String> availability) {
        userProfileService.addAvailabilityToUserProfile(userProfileId, availability);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

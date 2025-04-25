package com.software.userprofile.controller;

import com.software.userprofile.model.UserProfile;
import com.software.userprofile.model.SkillEntity;
import com.software.userprofile.model.UserProfileAvailability;
import com.software.userprofile.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
        List<UserProfile> userProfiles = userProfileService.getUserProfiles();
        return ResponseEntity.ok(userProfiles);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Application is running!");
    }

    @GetMapping("/{fullName}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String fullName) {
        Optional<UserProfile> userProfile = Optional.ofNullable(userProfileService.getUserProfile(fullName));

        return userProfile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Map<String, Object> addUserProfile(@RequestBody UserProfile userProfile) {
        UserProfile savedProfile = userProfileService.saveUserProfile(userProfile);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User profile saved successfully");
        response.put("userProfile", savedProfile);
        return response;
    }

    @PutMapping("/{userProfileId}/skills")
    public ResponseEntity<Void> addSkillsToUserProfile(@PathVariable Long userProfileId, @RequestBody List<SkillEntity> skills) {
        userProfileService.addSkillsToUserProfile(userProfileId, skills);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userProfileId}/availability")
    public ResponseEntity<Void> addAvailabilityToUserProfile(@PathVariable Long userProfileId, @RequestBody List<UserProfileAvailability> availabilityList) {
        userProfileService.addAvailabilityToUserProfile(userProfileId, availabilityList);
        return ResponseEntity.ok().build();
    }
}

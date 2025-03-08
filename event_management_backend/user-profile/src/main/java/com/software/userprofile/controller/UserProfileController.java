package com.software.userprofile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.software.userprofile.model.UserProfile;
import com.software.userprofile.service.UserProfileService;

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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found");
    }
    return userProfile;
}

@PostMapping("/add")
public ResponseEntity<UserProfile> addUserProfile(@RequestBody UserProfile userProfile) {
    UserProfile savedProfile = userProfileService.saveUserProfile(userProfile);
    return ResponseEntity.ok(savedProfile);
}


    @PostMapping("/userprofile")
public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) {
    // Process the user profile (e.g., save it, validate it)
    // In this example, we're simply returning the same profile for testing

    return ResponseEntity.ok(userProfile);  // This sends the userProfile back as JSON
}

}

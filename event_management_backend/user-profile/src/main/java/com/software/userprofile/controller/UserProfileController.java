package com.software.userprofile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{fullName}")
public UserProfile getUserProfile(@PathVariable String fullName) {
    UserProfile userProfile = userProfileService.getUserProfile(fullName);
    if (userProfile == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found");
    }
    return userProfile;
}

    @PostMapping("/add")
    public void addUserProfile(@RequestBody UserProfile userProfile) {
        userProfileService.addUserProfile(userProfile);
    }
}

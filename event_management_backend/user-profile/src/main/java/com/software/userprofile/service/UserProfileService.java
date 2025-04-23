package com.software.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.userprofile.model.UserProfile;
import com.software.userprofile.repository.UserProfileRepository;
import com.software.userprofile.repository.UserProfileSkillsRepository;
import com.software.userprofile.repository.UserProfileAvailabilityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileSkillsRepository userProfileSkillsRepository;

    @Autowired
    private UserProfileAvailabilityRepository userProfileAvailabilityRepository;

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getUserProfile(String fullName) {
        return userProfileRepository.findByFullName(fullName);
    }

    public UserProfile saveUserProfile(UserProfile userProfile) {
        // Save the user profile itself
        UserProfile savedProfile = userProfileRepository.save(userProfile);

        // Add the associated skills
        if (userProfile.getSkills() != null) {
            userProfile.getSkills().forEach(skill -> {
                userProfileSkillsRepository.save(new UserProfileSkills(savedProfile.getId(), skill.getId()));
            });
        }

        // Add the associated availability
        if (userProfile.getAvailability() != null) {
            userProfile.getAvailability().forEach(avail -> {
                userProfileAvailabilityRepository.save(new UserProfileAvailability(savedProfile.getId(), avail));
            });
        }

        return savedProfile;
    }

    public void addSkillsToUserProfile(String userProfileId, List<SkillEntity> skills) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileId);
        if (userProfileOpt.isPresent()) {
            UserProfile userProfile = userProfileOpt.get();
            skills.forEach(skill -> {
                userProfileSkillsRepository.save(new UserProfileSkills(userProfileId, skill.getId()));
            });
        }
    }

    public void addAvailabilityToUserProfile(String userProfileId, List<String> availabilityList) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileId);
        if (userProfileOpt.isPresent()) {
            UserProfile userProfile = userProfileOpt.get();
            availabilityList.forEach(avail -> {
                userProfileAvailabilityRepository.save(new UserProfileAvailability(userProfileId, avail));
            });
        }
    }
}

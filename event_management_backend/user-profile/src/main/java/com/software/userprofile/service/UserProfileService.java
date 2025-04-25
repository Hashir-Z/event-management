package com.software.userprofile.service;

import com.software.userprofile.model.*;
import com.software.userprofile.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileSkillsRepository userProfileSkillsRepository;
    private final UserProfileAvailabilityRepository userProfileAvailabilityRepository;

    public UserProfileService(
            UserProfileRepository userProfileRepository,
            UserProfileSkillsRepository userProfileSkillsRepository,
            UserProfileAvailabilityRepository userProfileAvailabilityRepository
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileSkillsRepository = userProfileSkillsRepository;
        this.userProfileAvailabilityRepository = userProfileAvailabilityRepository;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getUserProfile(String fullName) {
        return userProfileRepository.findByFullName(fullName);
    }

    @Transactional
    public UserProfile saveUserProfile(UserProfile userProfile) {
        UserProfile savedProfile = userProfileRepository.save(userProfile);

        if (userProfile.getSkills() != null) {
            userProfile.getSkills().forEach(skill -> {
                userProfileSkillsRepository.save(new UserProfileSkills(savedProfile, skill.getSkill()));
            });
        }

        if (userProfile.getAvailability() != null) {
            userProfile.getAvailability().forEach(avail -> {
                userProfileAvailabilityRepository.save(new UserProfileAvailability(savedProfile, avail.getAvailableDate()));
            });
        }

        return savedProfile;
    }

    @Transactional
    public void addSkillsToUserProfile(Long userProfileId, List<SkillEntity> skills) {
        UserProfile userProfile = userProfileRepository.findById(String.valueOf(userProfileId))
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        skills.forEach(skill -> userProfileSkillsRepository.save(new UserProfileSkills(userProfile, skill)));
    }

    @Transactional
    public void addAvailabilityToUserProfile(Long userProfileId, List<UserProfileAvailability> availabilityList) {
        UserProfile userProfile = userProfileRepository.findById(String.valueOf(userProfileId))
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        availabilityList.forEach(avail -> userProfileAvailabilityRepository.save(new UserProfileAvailability(userProfile, avail.getAvailableDate())));
    }
}

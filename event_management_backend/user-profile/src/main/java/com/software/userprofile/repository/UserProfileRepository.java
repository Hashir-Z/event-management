package com.software.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.software.userprofile.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    UserProfile findByFullName(String fullName);
}

package com.software.userprofile.repository;

import com.software.userprofile.model.UserProfileAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileAvailabilityRepository extends JpaRepository<UserProfileAvailability, Long> {
}
package com.software.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.software.userprofile.model.SkillEntity;

public interface UserProfileSkillsRepository extends JpaRepository<SkillEntity, Long> {
}

package com.software.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.software.eventmanagement.model.SkillEntity;
import com.software.eventmanagement.model.Skill;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
    Optional<SkillEntity> findByName(Skill name);
}

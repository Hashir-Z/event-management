package com.software.skill;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface SkillRepository extends JpaRepository<Skill, Integer> {}

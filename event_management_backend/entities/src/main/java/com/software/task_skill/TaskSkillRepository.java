package com.software.task_skill;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskSkillRepository extends JpaRepository<TaskSkill, TaskSkillId> {}
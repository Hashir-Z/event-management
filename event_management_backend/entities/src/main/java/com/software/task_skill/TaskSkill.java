package com.software.task_skill;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@Entity
@Table(name = "task_skills")
@IdClass(TaskSkillId.class)
@Getter
@Setter
public class TaskSkill {
    @Id
    @Column(name="task_id")
    private Integer taskId;

    @Id
    @Column(name="skill_id")
    private Integer skillId;

    // Getters and Setters
}


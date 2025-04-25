package com.software.task_skill;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class TaskSkillId implements Serializable {
    private Integer taskId;
    private Integer skillId;

    public TaskSkillId() {}

    public TaskSkillId(Integer taskId, Integer skillId) {
        this.taskId = taskId;
        this.skillId = skillId;
    }

    // Getters and setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskSkillId)) return false;
        TaskSkillId that = (TaskSkillId) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, skillId);
    }
}


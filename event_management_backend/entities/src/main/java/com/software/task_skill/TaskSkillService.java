package com.software.task_skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import java.util.*;



@Service

public class TaskSkillService {
    @Autowired
    private TaskSkillRepository repository;

    public List<TaskSkill> findAll() { return repository.findAll(); }
    public TaskSkill save(TaskSkill taskSkill) { return repository.save(taskSkill); }
    public void delete(TaskSkillId id) { repository.deleteById(id); }
}

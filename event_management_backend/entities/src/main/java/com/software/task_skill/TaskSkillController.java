package com.software.task_skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;


@SpringBootApplication
@RestController
@RequestMapping("/api/task-skills")
public class TaskSkillController {
    @Autowired
    private TaskSkillService service;

    @GetMapping
    public List<TaskSkill> findAll() { return service.findAll(); }

    @PostMapping
    public TaskSkill save(@RequestBody TaskSkill taskSkill) { return service.save(taskSkill); }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TaskSkillId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
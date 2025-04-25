package com.software.skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;



@RestController
@RequestMapping("/api/skills")
public class SkillController {
    @Autowired
    private SkillService service;

    @GetMapping
    public List<Skill> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> findById(@PathVariable Integer id) {
        Skill skill = service.findById(id);
        return skill != null ? ResponseEntity.ok(skill) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Skill save(@RequestBody Skill skill) { return service.save(skill); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

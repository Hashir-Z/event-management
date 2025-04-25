package com.software.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;


@SpringBootApplication
@RequestMapping("/api/assignments")
@RestController
public class AssignmentController {
    @Autowired
    private AssignmentService service;

    @GetMapping

    public List<Assignment> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> findById(@PathVariable Integer id) {
        Assignment assignment = service.findById(id);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Assignment save(@RequestBody Assignment assignment) { return service.save(assignment); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
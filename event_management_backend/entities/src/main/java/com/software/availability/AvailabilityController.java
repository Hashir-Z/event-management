package com.software.availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;


@SpringBootApplication
@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    @Autowired
    private AvailabilityService service;

    @GetMapping
    public List<Availability> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Availability> findById(@PathVariable Integer id) {
        Availability availability = service.findById(id);
        return availability != null ? ResponseEntity.ok(availability) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Availability save(@RequestBody Availability availability) { return service.save(availability); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

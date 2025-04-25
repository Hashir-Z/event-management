package com.software.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;


@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {
    @Autowired
    private PreferenceService service;

    @GetMapping
    public List<Preference> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Preference> findById(@PathVariable Integer id) {
        Preference preference = service.findById(id);
        return preference != null ? ResponseEntity.ok(preference) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Preference save(@RequestBody Preference preference) { return service.save(preference); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
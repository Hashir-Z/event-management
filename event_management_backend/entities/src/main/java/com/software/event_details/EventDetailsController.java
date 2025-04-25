package com.software.event_details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@SpringBootApplication

@RestController
@RequestMapping("/api/events")
public class EventDetailsController {
    @Autowired
    private EventDetailsService service;

    @GetMapping
    public List<EventDetails> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetails> findById(@PathVariable Integer id) {
        EventDetails event = service.findById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public EventDetails save(@RequestBody EventDetails event) { return service.save(event); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
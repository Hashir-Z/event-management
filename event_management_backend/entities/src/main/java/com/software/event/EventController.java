package com.software.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@SpringBootApplication

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Integer id) {
        Event event = service.findById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Event save(@RequestBody Event event) { return service.save(event); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
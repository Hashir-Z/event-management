package com.software.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;


@SpringBootApplication
@RestController

@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        User user = service.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public User save(@RequestBody User user) { return service.save(user); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
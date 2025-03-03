package com.software.useraccessmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.software.clients.uam.UserAccessManagementRequest;
import com.software.clients.uam.UserResponse;
import com.software.useraccessmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/uam")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/save")
    public void addUser(@Valid @RequestBody UserAccessManagementRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/user")
    public Object getUser (@RequestParam(name = "token") final String token) {
        return userService.getUser(token);
    }

    @PostMapping(path = "/email")
    public Optional<UserResponse> findByEmail(@RequestBody final String email) {
        return userService.findByEmail(email);
    }
}

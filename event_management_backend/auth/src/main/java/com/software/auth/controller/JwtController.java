package com.software.auth.controller;

import lombok.RequiredArgsConstructor;
import com.software.auth.service.JwtService;
import com.software.clients.auth.JwtRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/validate")
    public boolean validateToken (@RequestBody JwtRecord jwtRecord) {
        return jwtService.isTokenValidated(jwtRecord.token());
    }
}

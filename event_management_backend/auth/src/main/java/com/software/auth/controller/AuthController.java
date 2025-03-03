package com.software.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.software.auth.dto.AuthRequest;
import com.software.auth.dto.AuthResponse;
import com.software.auth.dto.UserRecord;
import com.software.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public void register(@Valid @RequestBody UserRecord userRecord) {
        authService.register(userRecord);
    }

    @PostMapping("/token")
    public AuthResponse getToken (@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") final String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}

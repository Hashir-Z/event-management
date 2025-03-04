package com.software.auth.controller;

import lombok.RequiredArgsConstructor;
import com.software.auth.service.TokenService;
import com.software.clients.auth.TokenRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public Optional<TokenRecord> findByToken (@RequestParam final String token) {
        return tokenService.findByToken(token).map(t -> new TokenRecord(t.getId(), t.getUserId(), t.getToken(), t.getTokenType().toString(), t.isRevoked(), t.isExpired()));
    }
}

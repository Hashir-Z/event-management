package com.software.userhistory.controller;

import com.software.userhistory.dto.UserHistoryResponse;
import com.software.userhistory.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-history")
public class UserHistoryController {

    private final UserHistoryService service;

    @Autowired
    public UserHistoryController(UserHistoryService service) {
        this.service = service;
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<UserHistoryResponse>> getUserHistory(@PathVariable Long userId) {
        // Ensure userId is now Long
        List<UserHistoryResponse> userHistories = service.getUserHistory(userId);
        return ResponseEntity.ok(userHistories);
    }
}
package com.software.userhistory.dto;

public class UserHistoryResponse {
    private Long id; // Ensure this is Long
    private Long userId; // Ensure this is Long
    private String action;
    private String timestamp;

    // Constructor
    public UserHistoryResponse(Long id, Long userId, String action, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
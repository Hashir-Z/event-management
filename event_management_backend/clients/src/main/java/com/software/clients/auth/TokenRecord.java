package com.software.clients.auth;

public record TokenRecord(String id, String userId, String token, String tokenType, boolean revoked, boolean expired) {
}

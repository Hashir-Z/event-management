package com.software.clients.uam;

public record UserResponse(String id,
                           String fullName,
                           String email,
                           String password,
                           Boolean isAdmin) {
}
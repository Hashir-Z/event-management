package com.software.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRecord(
        @NotBlank(message = "fullName is required.")
        String fullName,

        @NotBlank(message = "email is required.")
        String email,

        @NotBlank(message = "password is required.")
        String password,

        boolean isAdmin
) {
}
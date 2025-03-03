package com.software.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String id;

    private String fullName;

    private String email;

    private String oktaId;

    private Boolean active;

    private String accessToken;

    private String refreshToken;
}

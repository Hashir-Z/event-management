package com.software.clients.uam;

import net.minidev.json.annotate.JsonIgnore;

public record UserDetailsRecord(String id,
                                String fullName,
                                String email,
                                @JsonIgnore String password,
                                @JsonIgnore String accessToken,
                                @JsonIgnore String refreshToken
) {
}

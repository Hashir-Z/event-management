package com.software.clients.uam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserAccessManagementRequest(@NotBlank(message = "fullName is required.") String fullName,
                                          @NotBlank(message = "email is required.") String email,
                                          @NotBlank(message = "password is required.") String password,
                                          @NotNull(message = "isAdmin is required") Boolean isAdmin){
}


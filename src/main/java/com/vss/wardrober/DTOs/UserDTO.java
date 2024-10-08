package com.vss.wardrober.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "email is required")
        String email
) {
}

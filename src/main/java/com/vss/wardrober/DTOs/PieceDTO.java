package com.vss.wardrober.DTOs;

import jakarta.validation.constraints.NotBlank;

public record PieceDTO(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "category is required")
        String category,
        String brand,
        Boolean favorite
) {
}

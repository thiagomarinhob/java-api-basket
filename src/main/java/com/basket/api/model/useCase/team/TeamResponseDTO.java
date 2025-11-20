package com.basket.api.model.useCase.team;

import com.basket.api.model.useCase.category.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public record TeamResponseDTO(
        UUID id,
        String name,
        String shortName,
        String logoUrl,
        String location,
        String description,
        Integer ranking,
        List<CategoryResponseDTO> categories
) {
}

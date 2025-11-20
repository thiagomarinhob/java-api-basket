package com.basket.api.model.useCase.team;

import com.basket.api.model.useCase.category.CategoryResponse;

import java.util.List;
import java.util.UUID;

public record TeamResponse(
        UUID id,
        String name,
        String shortName,
        String logoUrl,
        String location,
        String description,
        Integer ranking,
        List<CategoryResponse> categories
) {
}

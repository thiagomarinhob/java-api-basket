package com.basket.api.domain.useCase.teamCategory;

import com.basket.api.domain.entity.CategoryGender;

import java.util.UUID;

public record ListCategoryResponse(
        UUID id,
        String name,
        CategoryGender categoryGender

) {
}

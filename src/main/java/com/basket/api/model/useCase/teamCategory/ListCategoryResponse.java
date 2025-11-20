package com.basket.api.model.useCase.teamCategory;

import com.basket.api.model.entity.CategoryGender;

import java.util.UUID;

public record ListCategoryResponse(
        UUID id,
        String name,
        CategoryGender categoryGender

) {
}

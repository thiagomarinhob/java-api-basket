package com.basket.api.domain.useCase.category;

import com.basket.api.domain.entity.CategoryGender;

import java.util.UUID;

public record CategoryResponse(UUID id, String name, String description, CategoryGender categoryGender) {
}

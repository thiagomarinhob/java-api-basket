package com.basket.api.model.useCase.category;

import com.basket.api.model.entity.CategoryGender;

import java.util.UUID;

public record CategoryResponseDTO(UUID id, String name, String description, CategoryGender categoryGender) {
}

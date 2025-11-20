package com.basket.api.model.useCase.category;

import com.basket.api.model.entity.CategoryGender;

public record CategoryRequestDTO(String name, CategoryGender categoryGender, String description) {
}

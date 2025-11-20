package com.basket.api.domain.useCase.category;

import com.basket.api.domain.entity.CategoryGender;

public record CategoryRequest(String name, CategoryGender categoryGender, String description) {
}

package com.basket.api.model.useCase.teamCategory;

import java.util.UUID;

public record TeamCategoryRequest(UUID teamId, UUID categoryId) {
}

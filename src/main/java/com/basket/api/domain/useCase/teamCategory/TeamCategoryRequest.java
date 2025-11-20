package com.basket.api.domain.useCase.teamCategory;

import java.util.UUID;

public record TeamCategoryRequest(UUID teamId, UUID categoryId) {
}

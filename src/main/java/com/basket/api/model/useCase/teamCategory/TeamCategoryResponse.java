package com.basket.api.model.useCase.teamCategory;

import java.util.UUID;

public record TeamCategoryResponse(UUID teamCategoryId, UUID teamId, UUID categoryId, String teamName, String categoryName) {
}

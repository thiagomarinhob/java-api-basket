package com.basket.api.model.useCase.teamCategory;

import java.util.UUID;

public record TeamCategoryResponseDTO(UUID teamCategoryId, UUID teamId, UUID categoryId, String teamName, String categoryName) {
}
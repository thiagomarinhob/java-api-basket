package com.basket.api.model.useCase.teamCategory;

import java.util.UUID;

public record TeamCategoryRequestDTO(UUID teamId, UUID categoryId) {
}

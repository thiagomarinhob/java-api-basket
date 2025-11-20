package com.basket.api.model.useCase.teamPlayer;

import java.util.UUID;

public record ListPlayersByTeamAndCategoryRequest(UUID teamId, UUID categoryId) {
}

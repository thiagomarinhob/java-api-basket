package com.basket.api.domain.useCase.teamPlayer;

import java.util.UUID;

public record ListPlayersByTeamAndCategoryRequest(UUID teamId, UUID categoryId) {
}

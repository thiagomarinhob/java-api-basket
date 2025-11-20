package com.basket.api.domain.useCase.teamPlayer;

import java.util.UUID;

public record TeamPlayerRequest(UUID playerId, UUID teamId, UUID categoryId ) {
}

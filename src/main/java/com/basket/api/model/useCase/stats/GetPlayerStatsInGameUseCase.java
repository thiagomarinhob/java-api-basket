package com.basket.api.model.useCase.stats;

import java.util.UUID;

public interface GetPlayerStatsInGameUseCase {

    PlayerStatsResponse execute(UUID gameId, UUID playerId);
}

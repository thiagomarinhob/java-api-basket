package com.basket.api.domain.useCase.stats;

import java.util.UUID;

public interface GetPlayerStatsInGameUseCase {

    PlayerStatsResponse execute(UUID gameId, UUID playerId);
}

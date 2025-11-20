package com.basket.api.domain.useCase.stats;

import com.basket.api.domain.useCase.UseCase;

import java.util.UUID;

public interface GetGameStatsUseCase extends UseCase<UUID, GameStatsResponse> {
}

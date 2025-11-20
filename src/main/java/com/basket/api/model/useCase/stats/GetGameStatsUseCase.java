package com.basket.api.model.useCase.stats;

import com.basket.api.model.useCase.UseCase;

import java.util.UUID;

public interface GetGameStatsUseCase extends UseCase<UUID, GameStatsResponse> {
}

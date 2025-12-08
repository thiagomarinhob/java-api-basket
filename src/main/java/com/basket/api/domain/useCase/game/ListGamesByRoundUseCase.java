package com.basket.api.domain.useCase.game;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListGamesByRoundUseCase extends UseCase<ListGamesByRoundUseCase.Input, List<GameResponse>> {
    record Input(UUID leagueId, Integer round) {}
}


package com.basket.api.domain.useCase.game;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListGameByLeagueIdUseCase extends UseCase<UUID, List<GameResponse>> {
}

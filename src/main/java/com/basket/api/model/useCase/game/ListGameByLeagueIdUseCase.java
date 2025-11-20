package com.basket.api.model.useCase.game;

import com.basket.api.model.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListGameByLeagueIdUseCase extends UseCase<UUID, List<GameResponse>> {
}

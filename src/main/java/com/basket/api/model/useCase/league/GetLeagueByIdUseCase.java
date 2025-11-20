package com.basket.api.model.useCase.league;

import com.basket.api.model.useCase.UseCase;

import java.util.UUID;

public interface GetLeagueByIdUseCase extends UseCase<UUID, LeagueResponse> {
}

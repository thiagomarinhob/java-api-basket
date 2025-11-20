package com.basket.api.domain.useCase.league;

import com.basket.api.domain.useCase.UseCase;

import java.util.UUID;

public interface GetLeagueByIdUseCase extends UseCase<UUID, LeagueResponse> {
}

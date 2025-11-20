package com.basket.api.model.useCase.league;

import com.basket.api.model.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface GetLeagueStandingsUseCase extends UseCase<UUID, List<TeamStandingsResponse>> {
}

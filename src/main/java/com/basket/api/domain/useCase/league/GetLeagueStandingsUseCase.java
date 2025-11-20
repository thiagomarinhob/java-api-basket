package com.basket.api.domain.useCase.league;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface GetLeagueStandingsUseCase extends UseCase<UUID, List<TeamStandingsResponse>> {
}

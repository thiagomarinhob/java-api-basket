package com.basket.api.domain.useCase.leagueTeam;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListLeagueTeamsUseCase extends UseCase<UUID, List<ListTeamResponse>> {
}

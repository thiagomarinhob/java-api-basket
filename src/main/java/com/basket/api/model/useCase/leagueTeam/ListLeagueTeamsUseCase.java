package com.basket.api.model.useCase.leagueTeam;

import com.basket.api.model.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListLeagueTeamsUseCase extends UseCase<UUID, List<ListTeamResponse>> {
}

package com.basket.api.domain.useCase.leagueTeam;

import java.util.UUID;

public record AddTeamToLeagueRequest(UUID leagueId, UUID teamId) {
}

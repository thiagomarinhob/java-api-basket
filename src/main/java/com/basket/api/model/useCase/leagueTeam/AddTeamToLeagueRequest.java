package com.basket.api.model.useCase.leagueTeam;

import java.util.UUID;

public record AddTeamToLeagueRequest(UUID leagueId, UUID teamId) {
}

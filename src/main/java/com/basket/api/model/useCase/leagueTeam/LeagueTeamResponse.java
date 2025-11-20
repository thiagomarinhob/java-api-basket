package com.basket.api.model.useCase.leagueTeam;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeagueTeamResponse(
        UUID associationId,
        UUID leagueId,
        String leagueName,
        UUID teamId,
        String teamName,
        LocalDateTime createdAt
) {}

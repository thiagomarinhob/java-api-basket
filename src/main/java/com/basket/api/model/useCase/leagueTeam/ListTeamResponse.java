package com.basket.api.model.useCase.leagueTeam;

import java.util.UUID;

public record ListTeamResponse(
        UUID id,
        String name,
        String logoUrl,
        String location
) {
}

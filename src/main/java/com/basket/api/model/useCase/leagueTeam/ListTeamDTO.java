package com.basket.api.model.useCase.leagueTeam;

import java.util.UUID;

public record ListTeamDTO(
        UUID id,
        String name,
        String logoUrl,
        String location
) {
}

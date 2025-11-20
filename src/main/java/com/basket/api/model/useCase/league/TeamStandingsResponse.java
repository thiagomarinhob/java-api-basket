package com.basket.api.model.useCase.league;

import java.util.UUID;

public record TeamStandingsResponse(
        UUID teamId,
        String teamName,
        String teamLogoUrl,
        int position,
        int gamesPlayed,
        int wins,
        int losses,
        int points,
        int pointsFor,
        int pointsAgainst,
        int pointsDifference,
        double winningPercentage
) {}

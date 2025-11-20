package com.basket.api.domain.useCase.league;

import java.util.UUID;

public record ThreePointLeaderResponse(
        int rank,
        UUID playerId,
        String playerName,
        String teamName,
        String teamLogoUrl,
        int gamesPlayed,
        int totalThreePointers,
        int pointsFromThreePointers,
        double threePointersPerGame
) {}

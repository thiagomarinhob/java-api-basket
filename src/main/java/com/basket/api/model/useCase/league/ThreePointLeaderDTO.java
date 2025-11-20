package com.basket.api.model.useCase.league;

import java.util.UUID;

public record ThreePointLeaderDTO(
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
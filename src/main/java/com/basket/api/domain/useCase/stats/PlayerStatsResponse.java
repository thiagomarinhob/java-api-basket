package com.basket.api.domain.useCase.stats;

import java.util.UUID;

public record PlayerStatsResponse(
        UUID playerId,
        String playerName,
        String teamName,
        Integer points1,
        Integer points2,
        Integer points3,
        Integer totalPoints,
        Integer fouls,
        Integer assists,
        Integer rebounds
) {}

package com.basket.api.domain.useCase.stats;

import java.util.UUID;

public record PlayerStatsRequest(
        UUID playerId,
        UUID teamId,
        Integer points1,
        Integer points2,
        Integer points3,
        Integer fouls,
        Integer assists,
        Integer rebounds
) {}

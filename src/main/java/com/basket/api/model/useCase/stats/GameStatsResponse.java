package com.basket.api.model.useCase.stats;

import com.basket.api.model.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameStatsResponse(
        UUID gameId,
        String homeTeamName,
        String awayTeamName,
        Integer homeScore,
        Integer awayScore,
        LocalDateTime scheduledDate,
        GameStatus status,
        List<PlayerStatsResponse> playerStats
) {}

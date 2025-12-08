package com.basket.api.domain.useCase.game;

import com.basket.api.domain.entity.GameStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameRequest(
        UUID leagueId,
        UUID homeTeamId,
        UUID awayTeamId,
        String venue,
        LocalDateTime scheduledDate,
        Integer round,
        GameStatus status
) {
}

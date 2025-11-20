package com.basket.api.model.useCase.game;

import com.basket.api.model.entity.GameStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameRequest(
        UUID leagueId,
        UUID homeTeamId,
        UUID awayTeamId,
        String venue,
        LocalDateTime scheduledDate,
        GameStatus status
) {
}

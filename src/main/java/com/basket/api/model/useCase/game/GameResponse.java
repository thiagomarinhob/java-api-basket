package com.basket.api.model.useCase.game;

import com.basket.api.model.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponse(
        UUID id,
        GameLeagueResponse league,
        GameTeamResponse homeTeam,
        GameTeamResponse awayTeam,
        String venue,
        LocalDateTime scheduledDate,
        GameStatus status,
        Integer homeScore,
        Integer awayScore
) {}

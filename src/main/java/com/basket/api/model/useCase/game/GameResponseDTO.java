package com.basket.api.model.useCase.game;

import com.basket.api.model.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponseDTO(
        UUID id,
        GameLeagueResponseDTO league,
        GameTeamResponseDTO homeTeam,
        GameTeamResponseDTO awayTeam,
        String venue,
        LocalDateTime scheduledDate,
        GameStatus status,
        Integer homeScore,
        Integer awayScore
) {}
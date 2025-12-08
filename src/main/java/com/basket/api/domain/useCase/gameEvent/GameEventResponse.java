package com.basket.api.domain.useCase.gameEvent;

import com.basket.api.domain.entity.GameEventType;
import java.time.LocalDateTime;
import java.util.UUID;

public record GameEventResponse(
        UUID id,
        GameEventGameResponse game,
        GameEventPlayerResponse player,  // Pode ser null para eventos sem jogador
        GameEventTeamResponse team,
        GameEventType eventType,
        Integer eventTime,
        Integer points,
        LocalDateTime createdAt
) {}


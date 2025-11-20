package com.basket.api.domain.useCase.gameEvent;

import com.basket.api.domain.entity.GameEventType;

import java.util.UUID;

public record GameEventRequest(
        UUID gameId,
        UUID teamId,
        UUID playerId,
        GameEventType eventType,
        Integer eventTime,
        Integer points
) {
}

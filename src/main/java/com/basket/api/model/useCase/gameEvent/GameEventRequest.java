package com.basket.api.model.useCase.gameEvent;

import com.basket.api.model.entity.GameEventType;

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

package com.basket.api.model.useCase.teamPlayer;

import java.time.LocalDateTime;
import java.util.UUID;

public record TeamPlayerResponse(
        UUID associationId,
        UUID teamId,
        String teamName,
        UUID playerId,
        String playerName,
        UUID categoryId,
        String categoryName,
        Boolean isActive,
        LocalDateTime startDate
) {}
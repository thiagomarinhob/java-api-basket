package com.basket.api.domain.useCase.player;

import com.basket.api.domain.entity.PlayerPosition;

import java.util.Date;
import java.util.UUID;

public record PlayerResponse(
        UUID id,
        String firstName,
        String lastName,
        String document,
        String nickName,
        Date birthDate,
        Float height,
        Integer jerseyNumber,
        String photoURL,
        PlayerPosition position
) {
}


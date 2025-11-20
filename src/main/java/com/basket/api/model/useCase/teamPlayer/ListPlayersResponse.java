package com.basket.api.model.useCase.teamPlayer;

import java.util.Date;
import java.util.UUID;

public record ListPlayersResponse(
        UUID id,
        String firstName,
        String lastName,
        String document,
        String nickName,
        Date birthDate,
        Float height,
        Integer jerseyNumber,
        String photoURL,
        String categoryName
) {
}

package com.basket.api.model.useCase.league;

import java.time.LocalDate;
import java.util.UUID;

public record LeagueResponseDTO(
        UUID id,
        String name,
        String description,
        String logoUrl,
        LocalDate startDate,
        LocalDate endDate
) {}
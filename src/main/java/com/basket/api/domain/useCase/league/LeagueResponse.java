package com.basket.api.domain.useCase.league;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LeagueResponse(
        UUID id,
        String name,
        String description,
        String logoUrl,
        LocalDate startDate,
        LocalDate endDate
) {}

package com.basket.api.domain.useCase.league;

import com.basket.api.domain.useCase.category.CategoryResponse;

import java.time.LocalDate;
import java.util.UUID;

public record LeagueResponse(
        UUID id,
        String name,
        String description,
        String logoUrl,
        LocalDate startDate,
        LocalDate endDate,
        Integer minTeams,
        Integer maxTeams,
        CategoryResponse category
) {}

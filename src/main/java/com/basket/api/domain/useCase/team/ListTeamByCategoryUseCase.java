package com.basket.api.domain.useCase.team;

import java.util.List;
import java.util.UUID;

public interface ListTeamByCategoryUseCase {
    List<TeamResponse> execute(UUID categoryId);
}


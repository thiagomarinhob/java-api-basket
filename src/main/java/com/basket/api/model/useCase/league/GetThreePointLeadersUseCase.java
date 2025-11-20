package com.basket.api.model.useCase.league;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface GetThreePointLeadersUseCase {

    Page<ThreePointLeaderResponse> execute(UUID leagueId, PageRequest pageRequest);
}

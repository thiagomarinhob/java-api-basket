package com.basket.api.domain.useCase.league;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface GetTopScorersUseCase extends UseCase<UUID, List<TopScorerResponse>> {
}

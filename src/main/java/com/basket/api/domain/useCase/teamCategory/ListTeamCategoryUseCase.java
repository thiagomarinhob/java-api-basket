package com.basket.api.domain.useCase.teamCategory;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListTeamCategoryUseCase extends UseCase<UUID, List<ListCategoryResponse>> {
}

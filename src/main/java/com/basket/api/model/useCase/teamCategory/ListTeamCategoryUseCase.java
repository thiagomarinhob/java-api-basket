package com.basket.api.model.useCase.teamCategory;

import com.basket.api.model.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListTeamCategoryUseCase extends UseCase<UUID, List<ListCategoryResponse>> {
}

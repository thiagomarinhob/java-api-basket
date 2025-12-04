package com.basket.api.domain.useCase.player;

import com.basket.api.domain.useCase.UseCase;

import java.util.UUID;

public interface GetPlayerByIdUseCase extends UseCase<UUID, PlayerResponse> {
}


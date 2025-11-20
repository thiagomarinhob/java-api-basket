package com.basket.api.domain.useCase.teamPlayer;

import com.basket.api.domain.useCase.UseCase;

import java.util.List;
import java.util.UUID;

public interface ListPlayerByTeamUseCase extends UseCase<UUID, List<ListPlayersResponse>> {
}

package com.basket.api.web;

import com.basket.api.domain.useCase.stats.GameStatsResponse;
import com.basket.api.domain.useCase.stats.GetGameStatsUseCase;
import com.basket.api.domain.useCase.stats.GetPlayerStatsInGameUseCase;
import com.basket.api.domain.useCase.stats.PlayerStatsRequest;
import com.basket.api.domain.useCase.stats.PlayerStatsResponse;
import com.basket.api.domain.useCase.stats.RecordGameStatsUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GameStatsController implements GameStatsAPI {

    private final RecordGameStatsUseCase recordGameStatsUseCase;
    private final GetGameStatsUseCase getGameStatsUseCase;
    private final GetPlayerStatsInGameUseCase getPlayerStatsInGameUseCase;

    @Override
    public ResponseEntity<Void> recordGameStats(
            @PathVariable UUID gameId,
            @RequestBody List<PlayerStatsRequest> playerStatsList) {

        recordGameStatsUseCase.execute(gameId, playerStatsList);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GameStatsResponse> getGameStats(@PathVariable UUID gameId) throws AuthenticationException {
        GameStatsResponse response = getGameStatsUseCase.execute(gameId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PlayerStatsResponse> getPlayerStatsInGame(
            @PathVariable UUID gameId,
            @PathVariable UUID playerId) {
        PlayerStatsResponse response = getPlayerStatsInGameUseCase.execute(gameId, playerId);
        return ResponseEntity.ok(response);
    }
}

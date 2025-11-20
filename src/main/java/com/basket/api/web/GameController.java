package com.basket.api.web;

import com.basket.api.domain.useCase.game.CreateGameUseCase;
import com.basket.api.domain.useCase.game.GameRequest;
import com.basket.api.domain.useCase.game.GameResponse;
import com.basket.api.domain.useCase.game.ListGameByLeagueIdUseCase;
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
public class GameController implements GameAPI {

    private final CreateGameUseCase createGameUseCase;
    private final ListGameByLeagueIdUseCase listGameByLeagueIdUseCase;

    @Override
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) throws AuthenticationException {
        GameResponse game = createGameUseCase.execute(gameRequest);
        return ResponseEntity.ok(game);
    }

    @Override
    public ResponseEntity<List<GameResponse>> listGamesByLeague(@PathVariable UUID leagueId) throws AuthenticationException {
        List<GameResponse> list = listGameByLeagueIdUseCase.execute(leagueId);
        return ResponseEntity.ok(list);
    }
}

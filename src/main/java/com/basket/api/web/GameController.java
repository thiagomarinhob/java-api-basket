package com.basket.api.web;

import com.basket.api.domain.useCase.game.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
    private final StartGameUseCase startGameUseCase;
    private final FinishGameUseCase finishGameUseCase;

    @Override
    public GameResponse createGame(@RequestBody GameRequest gameRequest) throws AuthenticationException {
        return createGameUseCase.execute(gameRequest);
    }

    @Override
    public List<GameResponse> listGamesByLeague(@PathVariable UUID leagueId) throws AuthenticationException {
        return listGameByLeagueIdUseCase.execute(leagueId);
    }

    @Override
    public GameResponse startGame(@PathVariable UUID id) throws AuthenticationException {
        return startGameUseCase.execute(id);
    }

    @Override
    public GameResponse finishGame(@PathVariable UUID id) throws AuthenticationException {
        return finishGameUseCase.execute(id);
    }
}

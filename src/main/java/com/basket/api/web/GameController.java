package com.basket.api.web;

import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.useCase.game.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GameController implements GameAPI {

    private final CreateGameUseCase createGameUseCase;
    private final ListGameByLeagueIdUseCase listGameByLeagueIdUseCase;
    private final ListGamesByRoundUseCase listGamesByRoundUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final StartGameUseCase startGameUseCase;
    private final EndGameUseCase endGameUseCase;

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

    @Override
    public ResponseEntity<List<GameResponse>> listGamesByRound(
            @PathVariable UUID leagueId,
            @PathVariable Integer round
    ) throws AuthenticationException {
        List<GameResponse> list = listGamesByRoundUseCase.execute(
                new ListGamesByRoundUseCase.Input(leagueId, round)
        );
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<Page<GameResponse>> listGames(
            UUID leagueId,
            GameStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {

        Page<GameResponse> response = listGamesUseCase.execute(leagueId, status, startDate, endDate, pageable);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GameResponse> startGame(@PathVariable UUID gameId) throws AuthenticationException {
        GameResponse game = startGameUseCase.execute(gameId);
        return ResponseEntity.ok(game);
    }

    @Override
    public ResponseEntity<GameResponse> endGame(@PathVariable UUID gameId) throws AuthenticationException {
        GameResponse game = endGameUseCase.execute(gameId);
        return ResponseEntity.ok(game);
    }
}

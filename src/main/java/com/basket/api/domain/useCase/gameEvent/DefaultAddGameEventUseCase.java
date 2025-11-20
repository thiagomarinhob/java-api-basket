package com.basket.api.domain.useCase.gameEvent;


import com.basket.api.domain.repository.*;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultAddGameEventUseCase implements AddGameEventUseCase {

    private final GameRepository gameRepository;
    private final GameEventRepository gameEventRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    @Override
    @Transactional
    public GameEvent execute(GameEventRequest request) {
        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Player player = resolvePlayer(request);

        GameEvent event = new GameEvent();
        event.setGame(game);
        event.setTeam(team);
        event.setPlayer(player);
        event.setEventType(request.eventType());
        event.setEventTime(request.eventTime());
        event.setPoints(request.points());

        GameEvent savedEvent = gameEventRepository.save(event);

        if (player != null) {
            applyEventToStatsAndGame(game, team, player, request);
        }

        return savedEvent;
    }

    private Player resolvePlayer(GameEventRequest request) {
        if (request.playerId() == null) {
            return null;
        }
        return playerRepository.findById(request.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    }

    private void applyEventToStatsAndGame(Game game, Team team, Player player, GameEventRequest request) {
        PlayerGameStats stats = playerGameStatsRepository
                .findByGameIdAndPlayerId(game.getId(), player.getId())
                .orElseGet(() -> PlayerGameStats.newFor(game, team, player));

        stats.registerEvent(request);

        if (request.eventType() == GameEventType.POINTS) {
            int points = request.points() != null ? request.points() : 0;
            game.addPoints(team, points);
        }

        playerGameStatsRepository.save(stats);
        gameRepository.save(game);
    }
}

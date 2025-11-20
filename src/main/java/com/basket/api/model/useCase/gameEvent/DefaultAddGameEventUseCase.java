package com.basket.api.model.useCase.gameEvent;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.GameEvent;
import com.basket.api.model.entity.Player;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.GameEventRepository;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.repository.PlayerRepository;
import com.basket.api.model.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultAddGameEventUseCase implements AddGameEventUseCase {

    private final GameEventRepository gameEventRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public GameEvent execute(GameEventRequest gameEventRequest) {
        Game game = gameRepository.findById(gameEventRequest.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        Team team = teamRepository.findById(gameEventRequest.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Player player = null;
        if (gameEventRequest.playerId() != null) {
            player = playerRepository.findById(gameEventRequest.playerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        }

        GameEvent event = new GameEvent();
        event.setGame(game);
        event.setTeam(team);
        event.setPlayer(player);
        event.setEventType(gameEventRequest.eventType());
        event.setEventTime(gameEventRequest.eventTime());
        event.setPoints(gameEventRequest.points());

        return gameEventRepository.save(event);
    }
}

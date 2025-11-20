package com.basket.api.model.useCase.gameEvent;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.GameEvent;
import com.basket.api.model.repository.GameEventRepository;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.TeamRepository;
import com.basket.api.model.entity.Game;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.entity.Player;
import com.basket.api.model.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class AddGameEventUseCase {

    private final GameEventRepository gameEventRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final  TeamRepository teamRepository;

    public AddGameEventUseCase(GameEventRepository gameEventRepository, GameRepository gameRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.gameEventRepository = gameEventRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public GameEvent execute(GameEventRequestDTO gameEventRequestDTO) {
        Game game = gameRepository.findById(gameEventRequestDTO.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        Team team = teamRepository.findById(gameEventRequestDTO.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Player player = null;
        if (gameEventRequestDTO.playerId() != null) {
            player = playerRepository.findById(gameEventRequestDTO.playerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        }

        GameEvent event = new GameEvent();
        event.setGame(game);
        event.setTeam(team);
        event.setPlayer(player);
        event.setEventType(gameEventRequestDTO.eventType());
        event.setEventTime(gameEventRequestDTO.eventTime());
        event.setPoints(gameEventRequestDTO.points());

        return gameEventRepository.save(event);
    }
}

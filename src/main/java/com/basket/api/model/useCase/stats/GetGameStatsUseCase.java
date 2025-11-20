package com.basket.api.model.useCase.stats;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.Game;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetGameStatsUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    public GetGameStatsUseCase(GameRepository gameRepository, PlayerGameStatsRepository playerGameStatsRepository) {
        this.gameRepository = gameRepository;
        this.playerGameStatsRepository = playerGameStatsRepository;
    }

    public GameStatsResponseDTO execute(UUID gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        List<PlayerGameStats> statsList = playerGameStatsRepository.findByGameId(gameId);

        List<PlayerStatsResponseDTO> playerStatsDTOs = statsList.stream()
                .map(this::mapToPlayerStatsDTO)
                .collect(Collectors.toList());

        return new GameStatsResponseDTO(
                game.getId(),
                game.getHomeTeam().getName(),
                game.getAwayTeam().getName(),
                game.getHomeScore(),
                game.getAwayScore(),
                game.getScheduledDate(),
                game.getStatus(),
                playerStatsDTOs
        );
    }

    private PlayerStatsResponseDTO mapToPlayerStatsDTO(PlayerGameStats stats) {
        int totalPoints = (stats.getPoints1()) + (stats.getPoints2() * 2) + (stats.getPoints3() * 3);
        return new PlayerStatsResponseDTO(
                stats.getPlayer().getId(),
                stats.getPlayer().getFirstName() + " " + stats.getPlayer().getLastName(),
                stats.getTeam().getName(),
                stats.getPoints1(),
                stats.getPoints2(),
                stats.getPoints3(),
                totalPoints,
                stats.getFouls(),
                stats.getAssists(),
                stats.getRebounds()
        );
    }
}
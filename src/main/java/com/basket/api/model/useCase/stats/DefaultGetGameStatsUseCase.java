package com.basket.api.model.useCase.stats;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetGameStatsUseCase implements GetGameStatsUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    @Override
    public GameStatsResponse execute(UUID gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        List<PlayerGameStats> statsList = playerGameStatsRepository.findByGameId(gameId);

        List<PlayerStatsResponse> playerStatsResponses = statsList.stream()
                .map(this::mapToPlayerStats)
                .collect(Collectors.toList());

        return new GameStatsResponse(
                game.getId(),
                game.getHomeTeam().getName(),
                game.getAwayTeam().getName(),
                game.getHomeScore(),
                game.getAwayScore(),
                game.getScheduledDate(),
                game.getStatus(),
                playerStatsResponses
        );
    }

    private PlayerStatsResponse mapToPlayerStats(PlayerGameStats stats) {
        int totalPoints = (stats.getPoints1()) + (stats.getPoints2() * 2) + (stats.getPoints3() * 3);
        return new PlayerStatsResponse(
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

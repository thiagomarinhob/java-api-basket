package com.basket.api.model.useCase.stats;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPlayerStatsInGameUseCase {

    private final PlayerGameStatsRepository playerGameStatsRepository;

    public GetPlayerStatsInGameUseCase(PlayerGameStatsRepository playerGameStatsRepository) {
        this.playerGameStatsRepository = playerGameStatsRepository;
    }

    public PlayerStatsResponseDTO execute(UUID gameId, UUID playerId) {
        PlayerGameStats stats = playerGameStatsRepository.findByGameIdAndPlayerId(gameId, playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Estatísticas não encontradas para o jogador " + playerId + " na partida " + gameId));

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
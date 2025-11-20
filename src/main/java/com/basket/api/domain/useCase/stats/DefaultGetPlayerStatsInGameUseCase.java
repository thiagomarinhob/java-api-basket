package com.basket.api.domain.useCase.stats;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.PlayerGameStats;
import com.basket.api.domain.repository.PlayerGameStatsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetPlayerStatsInGameUseCase implements GetPlayerStatsInGameUseCase {

    private final PlayerGameStatsRepository playerGameStatsRepository;

    @Override
    public PlayerStatsResponse execute(UUID gameId, UUID playerId) {
        PlayerGameStats stats = playerGameStatsRepository.findByGameIdAndPlayerId(gameId, playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Estatísticas não encontradas para o jogador " + playerId + " na partida " + gameId));

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

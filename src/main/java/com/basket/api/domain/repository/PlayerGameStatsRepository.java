package com.basket.api.domain.repository;


import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.PlayerGameStats;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerGameStatsRepository extends JpaRepository<PlayerGameStats, UUID> {
    List<PlayerGameStats> findByGameId(UUID gameId);

    Optional<PlayerGameStats> findByGameIdAndPlayerId(UUID gameId, UUID playerId);

    List<PlayerGameStats> findByGameIn(List<Game> games);
}
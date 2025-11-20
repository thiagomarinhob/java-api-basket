package com.basket.api.model.repository;


import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.PlayerGameStats;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerGameStatsRepository extends JpaRepository<PlayerGameStats, UUID> {
    List<PlayerGameStats> findByGameId(UUID gameId);

    Optional<PlayerGameStats> findByGameIdAndPlayerId(UUID gameId, UUID playerId);

    List<PlayerGameStats> findByGameIn(List<Game> games);
}
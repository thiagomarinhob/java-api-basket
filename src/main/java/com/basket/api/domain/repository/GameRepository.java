package com.basket.api.domain.repository;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> findByLeagueId(UUID leagueId);

    List<Game> findByLeagueIdAndStatus(UUID leagueId, GameStatus status);
}

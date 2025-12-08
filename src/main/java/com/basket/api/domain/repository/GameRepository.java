package com.basket.api.domain.repository;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID>, JpaSpecificationExecutor<Game> {
    List<Game> findByLeagueId(UUID leagueId);

    List<Game> findByLeagueIdAndStatus(UUID leagueId, GameStatus status);

    List<Game> findByLeagueIdAndRound(UUID leagueId, Integer round);
}

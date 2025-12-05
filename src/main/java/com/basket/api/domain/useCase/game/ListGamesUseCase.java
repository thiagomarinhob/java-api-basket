package com.basket.api.domain.useCase.game;

import com.basket.api.domain.entity.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ListGamesUseCase {
    Page<GameResponse> execute(UUID leagueId, GameStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
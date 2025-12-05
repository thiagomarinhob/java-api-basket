package com.basket.api.domain.useCase.game;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.repository.GameRepository;
import com.basket.api.domain.repository.spec.GameSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListGamesUseCase implements ListGamesUseCase {

    private final GameRepository gameRepository;

    @Override
    public Page<GameResponse> execute(UUID leagueId, GameStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

        Specification<Game> spec = GameSpecification.withFilters(leagueId, status, startDate, endDate);

        Page<Game> gamesPage = gameRepository.findAll(spec, pageable);

        return gamesPage.map(this::mapToResponse);
    }

    private GameResponse mapToResponse(Game game) {
        return new GameResponse(
                game.getId(),
                new GameLeagueResponse(game.getLeague().getId(), game.getLeague().getName()),
                new GameTeamResponse(game.getHomeTeam().getId(), game.getHomeTeam().getName(), game.getHomeTeam().getLogoUrl()),
                new GameTeamResponse(game.getAwayTeam().getId(), game.getAwayTeam().getName(), game.getAwayTeam().getLogoUrl()),
                game.getVenue(),
                game.getScheduledDate(),
                game.getStatus(),
                game.getHomeScore(),
                game.getAwayScore()
        );
    }
}
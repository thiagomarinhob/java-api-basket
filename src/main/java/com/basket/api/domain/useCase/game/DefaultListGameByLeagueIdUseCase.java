package com.basket.api.domain.useCase.game;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.repository.GameRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListGameByLeagueIdUseCase implements ListGameByLeagueIdUseCase {

    private final GameRepository gameRepository;

    @Override
    public List<GameResponse> execute(UUID leagueId) {
        List<Game> games = gameRepository.findByLeagueId(leagueId);

        return games.stream().map(game -> new GameResponse(
                game.getId(),
                new GameLeagueResponse(game.getLeague().getId(), game.getLeague().getName()),
                new GameTeamResponse(game.getHomeTeam().getId(), game.getHomeTeam().getName(), game.getHomeTeam().getLogoUrl()),
                new GameTeamResponse(game.getAwayTeam().getId(), game.getAwayTeam().getName(), game.getAwayTeam().getLogoUrl()),
                game.getVenue(),
                game.getScheduledDate(),
                game.getStatus(),
                game.getHomeScore(),
                game.getAwayScore()
        )).collect(Collectors.toList());
    }
}

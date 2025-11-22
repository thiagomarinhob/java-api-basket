package com.basket.api.domain.useCase.game;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Category;
import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.repository.GameRepository;
import com.basket.api.domain.repository.LeagueRepository;
import com.basket.api.domain.repository.LeagueTeamRepository;
import com.basket.api.domain.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreateGameUseCase implements CreateGameUseCase {

    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final LeagueTeamRepository leagueTeamRepository;

    @Override
    public GameResponse execute(final GameRequest gameRequest) {
        final var league = leagueRepository.findById(gameRequest.leagueId())
                .orElseThrow(() -> new ResourceNotFoundException("League not found"));

        final var homeTeam = teamRepository.findById(gameRequest.homeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Home team not found"));

        final var awayTeam = teamRepository.findById(gameRequest.awayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Away team not found"));

        if (homeTeam.equals(awayTeam)) {
            throw new BusinessRuleException("Home team and away team cannot be the same");
        }

        final var leagueCategory = league.getCategory();

        boolean homeTeamInCategory = homeTeam.getCategoryEntityList().stream()
                .anyMatch(teamCategory -> teamCategory.getCategory().getId().equals(leagueCategory.getId()));

        if (!homeTeamInCategory) {
            throw new BusinessRuleException("O time da casa '" + homeTeam.getName() + "' não pertence à categoria da liga ('" + leagueCategory.getName() + "').");
        }

        boolean awayTeamInCategory = awayTeam.getCategoryEntityList().stream()
                .anyMatch(teamCategory -> teamCategory.getCategory().getId().equals(leagueCategory.getId()));

        if (!awayTeamInCategory) {
            throw new BusinessRuleException("O time visitante '" + awayTeam.getName() + "' não pertence à categoria da liga ('" + leagueCategory.getName() + "').");

        }

        if (!leagueTeamRepository.existsByLeagueAndTeam(league, homeTeam)) {
            throw new BusinessRuleException("O time da casa '" + homeTeam.getName() + "' não está inscrito nesta liga.");
        }

        if (!leagueTeamRepository.existsByLeagueAndTeam(league, awayTeam)) {
            throw new BusinessRuleException("O time visitante '" + awayTeam.getName() + "' não está inscrito nesta liga.");
        }

        Game game = new Game();
        game.setLeague(league);
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setVenue(gameRequest.venue());
        game.setScheduledDate(gameRequest.scheduledDate());
        game.setStatus(gameRequest.status() != null ? gameRequest.status() : GameStatus.SCHEDULED);

        Game savedGame = gameRepository.save(game);

        return GameResponse.builder()
                .id(savedGame.getId())
                .venue(savedGame.getVenue())
                .scheduledDate(savedGame.getScheduledDate())
                .status(savedGame.getStatus())
                .league(new GameLeagueResponse(savedGame.getLeague().getId(), savedGame.getLeague().getName()))
                .homeTeam(new GameTeamResponse(savedGame.getHomeTeam().getId(), savedGame.getHomeTeam().getName(), savedGame.getHomeTeam().getLogoUrl()))
                .awayTeam(new GameTeamResponse(savedGame.getAwayTeam().getId(), savedGame.getAwayTeam().getName(), savedGame.getAwayTeam().getLogoUrl()))
                .homeScore(savedGame.getHomeScore())
                .awayScore(savedGame.getAwayScore())
                .build();
    }
}

package com.basket.api.domain.useCase.league;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.entity.LeagueTeam;
import com.basket.api.domain.repository.GameRepository;
import com.basket.api.domain.repository.LeagueRepository;
import com.basket.api.domain.repository.LeagueTeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetLeagueStandingsUseCase implements GetLeagueStandingsUseCase {

    private final LeagueRepository leagueRepository;
    private final LeagueTeamRepository leagueTeamRepository;
    private final GameRepository gameRepository;

    @Override
    public List<TeamStandingsResponse> execute(UUID leagueId) {
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga não encontrada com o ID: " + leagueId));

        List<LeagueTeam> leagueTeams = leagueTeamRepository.findByLeagueId(league.getId());

        List<Game> completedGames = gameRepository.findByLeagueIdAndStatus(league.getId(), GameStatus.COMPLETED);

        List<TeamStandingsResponse> standings = new ArrayList<>();

        for (LeagueTeam leagueTeam : leagueTeams) {
            UUID teamId = leagueTeam.getTeam().getId();
            int wins = 0;
            int losses = 0;
            int pointsFor = 0;
            int pointsAgainst = 0;

            for (Game game : completedGames) {
                if (game.getHomeTeam().getId().equals(teamId)) {
                    pointsFor += game.getHomeScore();
                    pointsAgainst += game.getAwayScore();
                    if (game.getHomeScore() > game.getAwayScore()) {
                        wins++;
                    } else {
                        losses++;
                    }
                } else if (game.getAwayTeam().getId().equals(teamId)) {
                    pointsFor += game.getAwayScore();
                    pointsAgainst += game.getHomeScore();
                    if (game.getAwayScore() > game.getHomeScore()) {
                        wins++;
                    } else {
                        losses++;
                    }
                }
            }

            int gamesPlayed = wins + losses;
            int classificationPoints = (wins * 2) + (losses * 1);
            int pointsDifference = pointsFor - pointsAgainst;
            double winningPercentage = (gamesPlayed == 0) ? 0.0 : ((double) classificationPoints / (gamesPlayed * 2)) * 100;

            standings.add(new TeamStandingsResponse(
                    teamId,
                    leagueTeam.getTeam().getName(),
                    leagueTeam.getTeam().getLogoUrl(),
                    0,
                    gamesPlayed,
                    wins,
                    losses,
                    classificationPoints,
                    pointsFor,
                    pointsAgainst,
                    pointsDifference,
                    winningPercentage
            ));
        }

        standings.sort(Comparator.comparing(TeamStandingsResponse::points).reversed()
                .thenComparing(Comparator.comparing(TeamStandingsResponse::pointsDifference).reversed()));

        return IntStream.range(0, standings.size())
                .mapToObj(i -> {
                    TeamStandingsResponse s = standings.get(i);
                    return new TeamStandingsResponse(s.teamId(), s.teamName(), s.teamLogoUrl(), i + 1, s.gamesPlayed(), s.wins(), s.losses(), s.points(), s.pointsFor(), s.pointsAgainst(), s.pointsDifference(), s.winningPercentage());
                })
                .collect(Collectors.toList());
    }
}

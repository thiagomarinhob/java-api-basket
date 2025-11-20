package com.basket.api.model.useCase.league;

import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.GameStatus;
import com.basket.api.model.entity.Player;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetTopScorersUseCase implements GetTopScorersUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    @Override
    public List<TopScorerResponse> execute(UUID leagueId) {
        List<Game> completedGames = gameRepository.findByLeagueIdAndStatus(leagueId, GameStatus.COMPLETED);
        if (completedGames.isEmpty()) {
            return new ArrayList<>();
        }

        List<PlayerGameStats> allStats = playerGameStatsRepository.findByGameIn(completedGames);

        Map<Player, List<PlayerGameStats>> statsByPlayer = allStats.stream()
                .collect(Collectors.groupingBy(PlayerGameStats::getPlayer));

        List<TopScorerResponse> topScorers = new ArrayList<>();

        for (Map.Entry<Player, List<PlayerGameStats>> entry : statsByPlayer.entrySet()) {
            Player player = entry.getKey();
            List<PlayerGameStats> playerStats = entry.getValue();

            int gamesPlayed = playerStats.size();
            int totalPoints = playerStats.stream().mapToInt(s -> s.getPoints1() + (s.getPoints2() * 2) + (s.getPoints3() * 3)).sum();
            int totalFouls = playerStats.stream().mapToInt(PlayerGameStats::getFouls).sum();
            int totalAssists = playerStats.stream().mapToInt(PlayerGameStats::getAssists).sum();
            int totalRebounds = playerStats.stream().mapToInt(PlayerGameStats::getRebounds).sum();
            double pointsPerGame = (double) totalPoints / gamesPlayed;

            String teamName = playerStats.getLast().getTeam().getName();
            String teamLogoUrl = playerStats.getLast().getTeam().getLogoUrl();

            topScorers.add(new TopScorerResponse(
                    0, player.getId(), player.getFirstName() + " " + player.getLastName(),
                    teamName, teamLogoUrl, gamesPlayed, totalPoints, totalFouls, totalAssists,
                    totalRebounds, pointsPerGame
            ));
        }

        topScorers.sort(Comparator.comparing(TopScorerResponse::totalPoints).reversed());

        return IntStream.range(0, topScorers.size())
                .mapToObj(i -> {
                    TopScorerResponse s = topScorers.get(i);
                    return new TopScorerResponse(i + 1, s.playerId(), s.playerName(), s.teamName(), s.teamLogoUrl(), s.gamesPlayed(), s.totalPoints(), s.totalFouls(), s.totalAssists(), s.totalRebounds(), s.pointsPerGame());
                })
                .collect(Collectors.toList());
    }
}

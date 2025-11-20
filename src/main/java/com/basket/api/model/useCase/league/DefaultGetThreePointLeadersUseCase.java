package com.basket.api.model.useCase.league;

import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.GameStatus;
import com.basket.api.model.entity.Player;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetThreePointLeadersUseCase implements GetThreePointLeadersUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    @Override
    public Page<ThreePointLeaderResponse> execute(UUID leagueId, PageRequest pageRequest) {
        List<Game> completedGames = gameRepository.findByLeagueIdAndStatus(leagueId, GameStatus.COMPLETED);
        if (completedGames.isEmpty()) {
            return Page.empty(pageRequest);
        }

        List<PlayerGameStats> allStats = playerGameStatsRepository.findByGameIn(completedGames);

        Map<Player, List<PlayerGameStats>> statsByPlayer = allStats.stream()
                .collect(Collectors.groupingBy(PlayerGameStats::getPlayer));

        List<ThreePointLeaderResponse> threePointLeaders = new ArrayList<>();

        for (Map.Entry<Player, List<PlayerGameStats>> entry : statsByPlayer.entrySet()) {
            Player player = entry.getKey();
            List<PlayerGameStats> playerStats = entry.getValue();

            int gamesPlayed = playerStats.size();
            int totalThreePointers = playerStats.stream().mapToInt(PlayerGameStats::getPoints3).sum();
            int pointsFromThreePointers = totalThreePointers * 3;
            double threePointersPerGame = (double) totalThreePointers / gamesPlayed;

            String teamName = playerStats.getLast().getTeam().getName();
            String teamLogoUrl = playerStats.getLast().getTeam().getLogoUrl();

            threePointLeaders.add(new ThreePointLeaderResponse(
                    0, player.getId(), player.getFirstName() + " " + player.getLastName(),
                    teamName, teamLogoUrl, gamesPlayed, totalThreePointers, pointsFromThreePointers, threePointersPerGame
            ));
        }

        threePointLeaders.sort(Comparator.comparing(ThreePointLeaderResponse::totalThreePointers).reversed());

        List<ThreePointLeaderResponse> rankedLeaders = IntStream.range(0, threePointLeaders.size())
                .mapToObj(i -> {
                    ThreePointLeaderResponse s = threePointLeaders.get(i);
                    return new ThreePointLeaderResponse(i + 1, s.playerId(), s.playerName(), s.teamName(), s.teamLogoUrl(), s.gamesPlayed(), s.totalThreePointers(), s.pointsFromThreePointers(), s.threePointersPerGame());
                })
                .collect(Collectors.toList());

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), rankedLeaders.size());

        if (start > rankedLeaders.size()) {
            return new PageImpl<>(new ArrayList<>(), pageRequest, rankedLeaders.size());
        }

        List<ThreePointLeaderResponse> pageContent = rankedLeaders.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, rankedLeaders.size());
    }
}

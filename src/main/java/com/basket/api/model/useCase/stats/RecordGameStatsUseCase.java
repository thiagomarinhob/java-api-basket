package com.basket.api.model.useCase.stats;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.Game;
import com.basket.api.model.entity.GameStatus;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.entity.Player;
import com.basket.api.model.repository.PlayerRepository;
import com.basket.api.model.entity.PlayerGameStats;
import com.basket.api.model.repository.PlayerGameStatsRepository;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RecordGameStatsUseCase {

    private final PlayerGameStatsRepository playerGameStatsRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ValidateGameStatsUseCase validateGameStatsUseCase;

    public RecordGameStatsUseCase(
            PlayerGameStatsRepository playerGameStatsRepository,
            GameRepository gameRepository,
            PlayerRepository playerRepository,
            TeamRepository teamRepository, ValidateGameStatsUseCase validateGameStatsUseCase) {
        this.playerGameStatsRepository = playerGameStatsRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.validateGameStatsUseCase = validateGameStatsUseCase;
    }

    @Transactional
    public void execute(UUID gameId, List<PlayerStatsRequest> playerStatsList) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        if (game.getStatus() == GameStatus.COMPLETED) {
            throw new BusinessRuleException("Esta partida já foi finalizada e não pode ser alterada.");
        }

        validateGameStatsUseCase.execute(gameId, playerStatsList);


        AtomicInteger totalHomeScore = new AtomicInteger(0);
        AtomicInteger totalAwayScore = new AtomicInteger(0);

        playerStatsList.forEach(statsDTO -> {
            Player player = playerRepository.findById(statsDTO.playerId()).get();
            Team team = teamRepository.findById(statsDTO.teamId()).get();

            PlayerGameStats statsEntity = new PlayerGameStats();
            statsEntity.setGame(game);
            statsEntity.setPlayer(player);
            statsEntity.setTeam(team);
            statsEntity.setPoints1(statsDTO.points1());
            statsEntity.setPoints2(statsDTO.points2());
            statsEntity.setPoints3(statsDTO.points3());
            statsEntity.setFouls(statsDTO.fouls());
            statsEntity.setAssists(statsDTO.assists());
            statsEntity.setRebounds(statsDTO.rebounds());

            playerGameStatsRepository.save(statsEntity);

            int playerTotalPoints = (statsDTO.points1()) + (statsDTO.points2() * 2) + (statsDTO.points3() * 3);
            if (team.getId().equals(game.getHomeTeam().getId())) {
                totalHomeScore.addAndGet(playerTotalPoints);
            } else if (team.getId().equals(game.getAwayTeam().getId())) {
                totalAwayScore.addAndGet(playerTotalPoints);
            }
        });

        game.setHomeScore(totalHomeScore.get());
        game.setAwayScore(totalAwayScore.get());
        game.setStatus(GameStatus.COMPLETED);
        gameRepository.save(game);
    }
}

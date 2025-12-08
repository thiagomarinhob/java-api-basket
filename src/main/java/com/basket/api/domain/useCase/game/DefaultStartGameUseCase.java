package com.basket.api.domain.useCase.game;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.repository.GameRepository;
import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultStartGameUseCase implements StartGameUseCase {

    private final GameRepository gameRepository;

    @Override
    @Transactional
    public GameResponse execute(UUID gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            throw new BusinessRuleException("A partida já está em andamento.");
        }

        if (game.getStatus() == GameStatus.COMPLETED) {
            throw new BusinessRuleException("Não é possível iniciar uma partida que já foi finalizada.");
        }

        if (game.getStatus() == GameStatus.CANCELLED) {
            throw new BusinessRuleException("Não é possível iniciar uma partida que foi cancelada.");
        }

        game.setStatus(GameStatus.IN_PROGRESS);
        Game savedGame = gameRepository.save(game);

        return new GameResponse(
                savedGame.getId(),
                new GameLeagueResponse(savedGame.getLeague().getId(), savedGame.getLeague().getName()),
                new GameTeamResponse(savedGame.getHomeTeam().getId(), savedGame.getHomeTeam().getName(), savedGame.getHomeTeam().getLogoUrl()),
                new GameTeamResponse(savedGame.getAwayTeam().getId(), savedGame.getAwayTeam().getName(), savedGame.getAwayTeam().getLogoUrl()),
                savedGame.getVenue(),
                savedGame.getScheduledDate(),
                savedGame.getRound(),
                savedGame.getStatus(),
                savedGame.getHomeScore(),
                savedGame.getAwayScore()
        );
    }
}


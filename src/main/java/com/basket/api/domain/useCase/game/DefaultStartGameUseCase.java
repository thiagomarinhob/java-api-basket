package com.basket.api.domain.useCase.game;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import com.basket.api.domain.repository.GameRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        if (game.getStatus() != GameStatus.SCHEDULED) {
            throw new BusinessRuleException("Apenas jogos 'Agendados' podem ser iniciados. Status atual: " + game.getStatus());
        }

        game.setStatus(GameStatus.IN_PROGRESS);
        Game savedGame = gameRepository.save(game);

        return GameResponse.builder()
                .id(savedGame.getId())
                .status(savedGame.getStatus())
                .build();
    }
}
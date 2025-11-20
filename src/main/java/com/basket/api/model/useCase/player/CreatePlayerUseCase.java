package com.basket.api.model.useCase.player;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.model.entity.Player;
import com.basket.api.model.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public CreatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player execute(Player player) {
        if (playerRepository.findByDocument(player.getDocument()).isPresent()) {
            throw new BusinessRuleException("Document already exists: " + player.getDocument());
        }
        return playerRepository.save(player);
    }
}

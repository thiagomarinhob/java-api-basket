package com.basket.api.domain.useCase.player;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.domain.entity.Player;
import com.basket.api.domain.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreatePlayerUseCase implements CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public Player execute(Player player) {
        if (playerRepository.findByDocument(player.getDocument()).isPresent()) {
            throw new BusinessRuleException("Document already exists: " + player.getDocument());
        }
        return playerRepository.save(player);
    }
}

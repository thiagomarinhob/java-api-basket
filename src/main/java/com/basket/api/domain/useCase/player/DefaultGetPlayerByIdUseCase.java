package com.basket.api.domain.useCase.player;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Player;
import com.basket.api.domain.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetPlayerByIdUseCase implements GetPlayerByIdUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerResponse execute(UUID id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with ID: " + id));

        return new PlayerResponse(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getDocument(),
                player.getNickName(),
                player.getBirthDate(),
                player.getHeight(),
                player.getJerseyNumber(),
                player.getPhotoURL()
        );
    }
}


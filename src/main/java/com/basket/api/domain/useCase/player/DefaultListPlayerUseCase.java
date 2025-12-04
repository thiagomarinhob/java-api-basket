package com.basket.api.domain.useCase.player;

import com.basket.api.domain.entity.Player;
import com.basket.api.domain.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListPlayerUseCase implements ListPlayerUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public Page<PlayerResponse> execute(PageRequest pageRequest) {
        Page<Player> playersPage = playerRepository.findAll(pageRequest);

        return playersPage.map(this::convertToResponse);
    }

    private PlayerResponse convertToResponse(Player player) {
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


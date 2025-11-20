package com.basket.api.domain.useCase.teamPlayer;

import com.basket.api.domain.entity.Player;
import com.basket.api.domain.entity.TeamPlayer;
import com.basket.api.domain.repository.TeamPlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListPlayerByTeamUseCase implements ListPlayerByTeamUseCase {

    private final TeamPlayerRepository teamPlayerRepository;

    @Override
    public List<ListPlayersResponse> execute(UUID teamId) {
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeamIdAndIsActive(teamId, true);

        return teamPlayers.stream()
                .map(teamPlayer -> {
                    Player player = teamPlayer.getPlayer();
                    return new ListPlayersResponse(
                            player.getId(),
                            player.getFirstName(),
                            player.getLastName(),
                            player.getDocument(),
                            player.getNickName(),
                            player.getBirthDate(),
                            player.getHeight(),
                            player.getJerseyNumber(),
                            player.getPhotoURL(),
                            teamPlayer.getCategory().getName()
                    );
                })
                .toList();
    }
}

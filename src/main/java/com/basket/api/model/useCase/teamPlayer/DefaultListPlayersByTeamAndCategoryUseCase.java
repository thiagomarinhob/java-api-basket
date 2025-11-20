package com.basket.api.model.useCase.teamPlayer;

import com.basket.api.model.entity.Player;
import com.basket.api.model.entity.TeamPlayer;
import com.basket.api.model.repository.TeamPlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListPlayersByTeamAndCategoryUseCase implements ListPlayersByTeamAndCategoryUseCase {

    private final TeamPlayerRepository teamPlayerRepository;

    @Override
    public List<ListPlayersResponse> execute(ListPlayersByTeamAndCategoryRequest request) {
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeamIdAndCategoryIdAndIsActive(
                request.teamId(), request.categoryId(), true);

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
                .collect(Collectors.toList());
    }
}

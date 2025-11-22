package com.basket.api.web;

import com.basket.api.domain.useCase.teamPlayer.DefaultAddTeamPlayerUseCase;
import com.basket.api.domain.useCase.teamPlayer.DefaultListPlayerByTeamUseCase;
import com.basket.api.domain.useCase.teamPlayer.DefaultListPlayersByTeamAndCategoryUseCase;
import com.basket.api.domain.useCase.teamPlayer.ListPlayersByTeamAndCategoryRequest;
import com.basket.api.domain.useCase.teamPlayer.ListPlayersResponse;
import com.basket.api.domain.useCase.teamPlayer.TeamPlayerRequest;
import com.basket.api.domain.useCase.teamPlayer.TeamPlayerResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TeamPlayerController implements TeamPlayerAPI {

    private final DefaultAddTeamPlayerUseCase addTeamPlayerUseCase;
    private final DefaultListPlayerByTeamUseCase listPlayerByTeamUseCase;
    private final DefaultListPlayersByTeamAndCategoryUseCase listPlayersByTeamAndCategoryUseCase;

    @Override
    public TeamPlayerResponse addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId, @PathVariable UUID categoryId) {
        return addTeamPlayerUseCase.execute(new TeamPlayerRequest(playerId, teamId, categoryId));
    }

    @Override
    public List<ListPlayersResponse> listPlayerByTeam(@PathVariable UUID teamId) {
        return listPlayerByTeamUseCase.execute(teamId);
    }

    @Override
    public List<ListPlayersResponse> listPlayerByTeamAndCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        return listPlayersByTeamAndCategoryUseCase.execute(new ListPlayersByTeamAndCategoryRequest(teamId, categoryId));
    }
}

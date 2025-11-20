package com.basket.api.web;

import com.basket.api.model.useCase.teamPlayer.DefaultAddTeamPlayerUseCase;
import com.basket.api.model.useCase.teamPlayer.DefaultListPlayerByTeamUseCase;
import com.basket.api.model.useCase.teamPlayer.DefaultListPlayersByTeamAndCategoryUseCase;
import com.basket.api.model.useCase.teamPlayer.ListPlayersByTeamAndCategoryRequest;
import com.basket.api.model.useCase.teamPlayer.ListPlayersResponse;
import com.basket.api.model.useCase.teamPlayer.TeamPlayerRequest;
import com.basket.api.model.useCase.teamPlayer.TeamPlayerResponse;
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
    public ResponseEntity<TeamPlayerResponse> addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId, @PathVariable UUID categoryId) {
        TeamPlayerRequest requestDTO = new TeamPlayerRequest(playerId, teamId, categoryId);
        var result = this.addTeamPlayerUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<ListPlayersResponse>> listPlayerByTeam(@PathVariable UUID teamId) {
        var result = listPlayerByTeamUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<List<ListPlayersResponse>> listPlayerByTeamAndCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        var request = new ListPlayersByTeamAndCategoryRequest(teamId, categoryId);
        var result = listPlayersByTeamAndCategoryUseCase.execute(request);
        return ResponseEntity.ok().body(result);
    }
}

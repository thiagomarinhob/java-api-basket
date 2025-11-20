package com.basket.api.web;

import com.basket.api.model.useCase.leagueTeam.AddTeamToLeagueRequest;
import com.basket.api.model.useCase.leagueTeam.DefaultAddTeamToLeagueUseCase;
import com.basket.api.model.useCase.leagueTeam.DefaultListLeagueTeamsUseCase;
import com.basket.api.model.useCase.leagueTeam.ListTeamResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LeagueTeamController implements LeagueTeamAPI {

    private final DefaultAddTeamToLeagueUseCase addTeamToLeagueUseCase;
    private final DefaultListLeagueTeamsUseCase listLeagueTeamsUseCase;

    @Override
    public ResponseEntity<Object> addTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        var request = new AddTeamToLeagueRequest(leagueId, teamId);
        var result = this.addTeamToLeagueUseCase.execute(request);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<List<ListTeamResponse>> getTeam(@PathVariable UUID leagueId) {
        List<ListTeamResponse> result = listLeagueTeamsUseCase.execute(leagueId);
        return ResponseEntity.ok().body(result);
    }
}

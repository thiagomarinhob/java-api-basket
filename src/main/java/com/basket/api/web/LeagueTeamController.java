package com.basket.api.web;

import com.basket.api.domain.useCase.leagueTeam.AddTeamToLeagueRequest;
import com.basket.api.domain.useCase.leagueTeam.DefaultAddTeamToLeagueUseCase;
import com.basket.api.domain.useCase.leagueTeam.DefaultListLeagueTeamsUseCase;
import com.basket.api.domain.useCase.leagueTeam.ListTeamResponse;
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
    public Object addTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        return addTeamToLeagueUseCase.execute(new AddTeamToLeagueRequest(leagueId, teamId));
    }

    @Override
    public List<ListTeamResponse> getTeam(@PathVariable UUID leagueId) {
        return listLeagueTeamsUseCase.execute(leagueId);
    }
}

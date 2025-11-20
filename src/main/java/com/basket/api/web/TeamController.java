package com.basket.api.web;

import com.basket.api.model.entity.Team;
import com.basket.api.model.useCase.team.CreateTeamUseCase;
import com.basket.api.model.useCase.team.ListTeamUseCase;
import com.basket.api.model.useCase.team.TeamResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import javax.naming.AuthenticationException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TeamController implements TeamAPI {

    private final CreateTeamUseCase createTeamUseCase;
    private final ListTeamUseCase listTeamUseCase;

    @Override
    public ResponseEntity<Object> createTeam(@Valid @RequestBody Team team) throws AuthenticationException {
        var result = this.createTeamUseCase.execute(team);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<TeamResponse> getTeam(@PathVariable UUID id) throws AuthenticationException {
        var result = this.listTeamUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }
}

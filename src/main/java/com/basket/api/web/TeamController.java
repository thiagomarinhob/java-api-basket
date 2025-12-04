package com.basket.api.web;

import com.basket.api.domain.entity.Team;
import com.basket.api.domain.useCase.team.CreateTeamUseCase;
import com.basket.api.domain.useCase.team.GetTeamByIdUseCase;
import com.basket.api.domain.useCase.team.ListTeamUseCase;
import com.basket.api.domain.useCase.team.TeamResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import javax.naming.AuthenticationException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TeamController implements TeamAPI {

    private final CreateTeamUseCase createTeamUseCase;
    private final GetTeamByIdUseCase getTeamByIdUseCase;
    private final ListTeamUseCase listTeamUseCase;

    @Override
    public ResponseEntity<Object> createTeam(@Valid @RequestBody Team team) throws AuthenticationException {
        var result = this.createTeamUseCase.execute(team);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<TeamResponse> getTeam(@PathVariable UUID id) throws AuthenticationException {
        var result = this.getTeamByIdUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Page<TeamResponse>> getAllTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TeamResponse> teamsPage = this.listTeamUseCase.execute(pageRequest);
        return ResponseEntity.ok(teamsPage);
    }
}

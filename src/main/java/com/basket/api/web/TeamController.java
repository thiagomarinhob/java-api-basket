package com.basket.api.web;

import com.basket.api.domain.entity.Team;
import com.basket.api.domain.useCase.team.CreateTeamUseCase;
import com.basket.api.domain.useCase.team.ListTeamUseCase;
import com.basket.api.domain.useCase.team.TeamResponse;
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
    public Object createTeam(@Valid @RequestBody Team team) throws AuthenticationException {
        return createTeamUseCase.execute(team);
    }

    @Override
    public TeamResponse getTeam(@PathVariable UUID id) throws AuthenticationException {
        return listTeamUseCase.execute(id);
    }
}

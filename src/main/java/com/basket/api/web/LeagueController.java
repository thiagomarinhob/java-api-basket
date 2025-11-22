package com.basket.api.web;

import com.basket.api.domain.useCase.league.CreateLeagueUseCase;
import com.basket.api.domain.useCase.league.GetLeagueByIdUseCase;
import com.basket.api.domain.useCase.league.GetLeagueStandingsUseCase;
import com.basket.api.domain.useCase.league.LeagueRequest;
import com.basket.api.domain.useCase.league.LeagueResponse;
import com.basket.api.domain.useCase.league.ListLeagueUseCase;
import com.basket.api.domain.useCase.league.TeamStandingsResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LeagueController implements LeagueAPI {

    private final CreateLeagueUseCase createLeagueUseCase;
    private final GetLeagueByIdUseCase getLeagueByIdUseCase;
    private final ListLeagueUseCase listLeaguesUseCase;
    private final GetLeagueStandingsUseCase getLeagueStandingsUseCase;

    @Override
    public LeagueResponse createLeague(@Valid @RequestBody LeagueRequest leagueRequest) throws AuthenticationException {
        return createLeagueUseCase.execute(leagueRequest);
    }

    @Override
    public LeagueResponse getLeague(@PathVariable UUID id) throws AuthenticationException {
        return getLeagueByIdUseCase.execute(id);
    }

    @Override
    public List<LeagueResponse> getAllLeagues() {
        return listLeaguesUseCase.execute();
    }

    @Override
    public List<TeamStandingsResponse> getStandings(@PathVariable UUID leagueId) throws AuthenticationException {
        return getLeagueStandingsUseCase.execute(leagueId);
    }

}

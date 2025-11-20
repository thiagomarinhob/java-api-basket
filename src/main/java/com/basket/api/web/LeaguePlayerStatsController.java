package com.basket.api.web;

import com.basket.api.domain.useCase.league.GetThreePointLeadersUseCase;
import com.basket.api.domain.useCase.league.GetTopScorersUseCase;
import com.basket.api.domain.useCase.league.ThreePointLeaderResponse;
import com.basket.api.domain.useCase.league.TopScorerResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LeaguePlayerStatsController implements LeaguePlayerStatsAPI {

    private final GetTopScorersUseCase getTopScorersUseCase;
    private final GetThreePointLeadersUseCase getThreePointLeadersUseCase;

    @Override
    public ResponseEntity<List<TopScorerResponse>> getTopScorers(@PathVariable UUID leagueId) throws AuthenticationException {
        List<TopScorerResponse> topScorers = getTopScorersUseCase.execute(leagueId);
        return ResponseEntity.ok(topScorers);
    }

    @Override
    public ResponseEntity<Page<ThreePointLeaderResponse>> getThreePointLeaders(
            @PathVariable UUID leagueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ThreePointLeaderResponse> threePointLeadersPage = getThreePointLeadersUseCase.execute(leagueId, pageRequest);
        return ResponseEntity.ok(threePointLeadersPage);
    }
}

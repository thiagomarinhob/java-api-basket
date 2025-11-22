package com.basket.api.web;

import com.basket.api.domain.useCase.league.ThreePointLeaderResponse;
import com.basket.api.domain.useCase.league.TopScorerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues/{leagueId}/player-stats")
@Tag(name = "Rankings de Jogadores da Liga", description = "Endpoints para obter os rankings de jogadores")
@SecurityRequirement(name = "bearer-key")
public interface LeaguePlayerStatsAPI {

    @GetMapping("/top-scorers")
    List<TopScorerResponse> getTopScorers(@PathVariable UUID leagueId) throws AuthenticationException;

    @GetMapping("/three-point-leaders")
    @Operation(summary = "Busca o ranking de cestinhas de 3 pontos da liga (paginado)",
            description = "Retorna uma lista paginada dos jogadores com mais cestas de 3 pontos. Use os parâmetros 'page' (começando em 0) e 'size' para navegar.")
    Page<ThreePointLeaderResponse> getThreePointLeaders(
            @PathVariable UUID leagueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);
}


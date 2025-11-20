package com.basket.api.web;

import com.basket.api.domain.useCase.stats.GameStatsResponse;
import com.basket.api.domain.useCase.stats.PlayerStatsRequest;
import com.basket.api.domain.useCase.stats.PlayerStatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@Tag(name = "Estatísticas de Jogo", description = "Endpoints para gerenciar estatísticas de jogos")
@SecurityRequirement(name = "bearer-key")
public interface GameStatsAPI {

    @PostMapping("/{gameId}/stats")
    @Operation(summary = "Registra as estatísticas de todos os jogadores de uma partida",
            description = "Ao final de um jogo, submete uma lista com as estatísticas consolidadas de cada jogador. O sistema irá calcular o placar final e marcar a partida como 'COMPLETED'.")
    ResponseEntity<Void> recordGameStats(
            @PathVariable UUID gameId,
            @RequestBody List<PlayerStatsRequest> playerStatsList);

    @GetMapping("/{gameId}/stats")
    @Operation(summary = "Busca as estatísticas completas de uma partida",
            description = "Retorna o placar final e a lista de estatísticas individuais de cada jogador que participou da partida.")
    ResponseEntity<GameStatsResponse> getGameStats(@PathVariable UUID gameId) throws AuthenticationException;

    @GetMapping("/{gameId}/players/{playerId}/stats")
    @Operation(summary = "Busca as estatísticas de um jogador específico em uma partida",
            description = "Retorna as estatísticas detalhadas de um único jogador para uma partida específica.")
    ResponseEntity<PlayerStatsResponse> getPlayerStatsInGame(
            @PathVariable UUID gameId,
            @PathVariable UUID playerId);
}


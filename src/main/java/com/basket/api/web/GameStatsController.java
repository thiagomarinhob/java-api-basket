package com.basket.api.web;

import com.basket.api.model.useCase.stats.GameStatsResponse;
import com.basket.api.model.useCase.stats.PlayerStatsRequest;
import com.basket.api.model.useCase.stats.PlayerStatsResponse;
import com.basket.api.model.useCase.stats.GetGameStatsUseCase;
import com.basket.api.model.useCase.stats.GetPlayerStatsInGameUseCase;
import com.basket.api.model.useCase.stats.RecordGameStatsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@Tag(name = "Estatísticas de Jogo", description = "Endpoints para gerenciar estatísticas de jogos")
@SecurityRequirement(name = "bearer-key")
public class GameStatsController {

    private final RecordGameStatsUseCase recordGameStatsUseCase;
    private final GetGameStatsUseCase getGameStatsUseCase;
    private final GetPlayerStatsInGameUseCase getPlayerStatsInGameUseCase;

    public GameStatsController(RecordGameStatsUseCase recordGameStatsUseCase, GetGameStatsUseCase getGameStatsUseCase, GetPlayerStatsInGameUseCase getPlayerStatsInGameUseCase) {
        this.recordGameStatsUseCase = recordGameStatsUseCase;
        this.getGameStatsUseCase = getGameStatsUseCase;
        this.getPlayerStatsInGameUseCase = getPlayerStatsInGameUseCase;
    }

    @PostMapping("/{gameId}/stats")
    @Operation(summary = "Registra as estatísticas de todos os jogadores de uma partida",
            description = "Ao final de um jogo, submete uma lista com as estatísticas consolidadas de cada jogador. O sistema irá calcular o placar final e marcar a partida como 'COMPLETED'.")
    public ResponseEntity<Void> recordGameStats(
            @PathVariable UUID gameId,
            @RequestBody List<PlayerStatsRequest> playerStatsList) {

        recordGameStatsUseCase.execute(gameId, playerStatsList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gameId}/stats")
    @Operation(summary = "Busca as estatísticas completas de uma partida",
            description = "Retorna o placar final e a lista de estatísticas individuais de cada jogador que participou da partida.")
    public ResponseEntity<GameStatsResponse> getGameStats(@PathVariable UUID gameId) {
        GameStatsResponse response = getGameStatsUseCase.execute(gameId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}/players/{playerId}/stats")
    @Operation(summary = "Busca as estatísticas de um jogador específico em uma partida",
            description = "Retorna as estatísticas detalhadas de um único jogador para uma partida específica.")
    public ResponseEntity<PlayerStatsResponse> getPlayerStatsInGame(
            @PathVariable UUID gameId,
            @PathVariable UUID playerId) {
        PlayerStatsResponse response = getPlayerStatsInGameUseCase.execute(gameId, playerId);
        return ResponseEntity.ok(response);
    }
}

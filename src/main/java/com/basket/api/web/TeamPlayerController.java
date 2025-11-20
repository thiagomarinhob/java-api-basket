package com.basket.api.web;

import com.basket.api.model.useCase.teamPlayer.DefaultAddTeamPlayerUseCase;
import com.basket.api.model.useCase.teamPlayer.DefaultListPlayerByTeamUseCase;
import com.basket.api.model.useCase.teamPlayer.DefaultListPlayersByTeamAndCategoryUseCase;
import com.basket.api.model.useCase.teamPlayer.ListPlayersByTeamAndCategoryRequest;
import com.basket.api.model.useCase.teamPlayer.ListPlayersResponse;
import com.basket.api.model.useCase.teamPlayer.TeamPlayerRequest;
import com.basket.api.model.useCase.teamPlayer.TeamPlayerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams/{teamId}")
@Tag(name = "9. Associações (Time-Jogador)", description = "Endpoints para associar jogadores a times")
@SecurityRequirement(name = "bearer-key")
public class TeamPlayerController {


    private final DefaultAddTeamPlayerUseCase addTeamPlayerUseCase;
    private final DefaultListPlayerByTeamUseCase listPlayerByTeamUseCase;
    private final DefaultListPlayersByTeamAndCategoryUseCase listPlayersByTeamAndCategoryUseCase;

    public TeamPlayerController(DefaultAddTeamPlayerUseCase addTeamPlayerUseCase,
                                DefaultListPlayerByTeamUseCase listPlayerByTeamUseCase,
                                DefaultListPlayersByTeamAndCategoryUseCase listPlayersByTeamAndCategoryUseCase) {
        this.addTeamPlayerUseCase = addTeamPlayerUseCase;
        this.listPlayerByTeamUseCase = listPlayerByTeamUseCase;
        this.listPlayersByTeamAndCategoryUseCase = listPlayersByTeamAndCategoryUseCase;
    }

    @PostMapping("player/{playerId}/category/{categoryId}")
    @Operation(summary = "Adiciona um jogador a um time", description = "Cria a associação entre um jogador e um time. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Time ou jogador não encontrado"),
            @ApiResponse(responseCode = "409", description = "Jogador já pertence a este time")
    })
    public ResponseEntity<TeamPlayerResponse> addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId, @PathVariable UUID categoryId) {
        TeamPlayerRequest requestDTO = new TeamPlayerRequest(playerId, teamId, categoryId);
        var result = this.addTeamPlayerUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/players")
    @Operation(summary = "Lista os jogadores de um time", description = "Retorna uma lista com todos os jogadores ativos associados a um time específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    public ResponseEntity<List<ListPlayersResponse>> listPlayerByTeam(@PathVariable UUID teamId) {
        var result = listPlayerByTeamUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/category/{categoryId}/players")
    @Operation(summary = "Lista os jogadores de uma equipe específica (time e categoria)", description = "Retorna uma lista com todos os jogadores ativos associados a um time e com a mesma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida. Retorna uma lista de jogadores, que pode estar vazia se não houver jogadores na equipe.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListPlayersResponse.class)))),

            @ApiResponse(responseCode = "404", description = "Time ou Categoria não encontrada. Ocorre se o ID do time ou o ID da categoria não existir no sistema.",
                    content = @Content)
    })
    public ResponseEntity<List<ListPlayersResponse>> listPlayerByTeamAndCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        var request = new ListPlayersByTeamAndCategoryRequest(teamId, categoryId);
        var result = listPlayersByTeamAndCategoryUseCase.execute(request);
        return ResponseEntity.ok().body(result);
    }
}

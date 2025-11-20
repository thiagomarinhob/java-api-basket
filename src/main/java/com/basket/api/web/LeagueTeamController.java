package com.basket.api.web;

import com.basket.api.model.useCase.leagueTeam.AddTeamToLeagueRequest;
import com.basket.api.model.useCase.leagueTeam.DefaultAddTeamToLeagueUseCase;
import com.basket.api.model.useCase.leagueTeam.DefaultListLeagueTeamsUseCase;
import com.basket.api.model.useCase.leagueTeam.ListTeamResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues/{leagueId}/teams")
@Tag(name = "8. Associações (Liga-Time)", description = "Endpoints para associar times a ligas")
@SecurityRequirement(name = "bearer-key")
public class LeagueTeamController {


    private final DefaultAddTeamToLeagueUseCase addTeamToLeagueUseCase;
    private final DefaultListLeagueTeamsUseCase listLeagueTeamsUseCase;

    public LeagueTeamController(DefaultAddTeamToLeagueUseCase addTeamToLeagueUseCase,
                                DefaultListLeagueTeamsUseCase listLeagueTeamsUseCase) {
        this.addTeamToLeagueUseCase = addTeamToLeagueUseCase;
        this.listLeagueTeamsUseCase = listLeagueTeamsUseCase;
    }

    @PostMapping("/{teamId}")
    @Operation(summary = "Adiciona um time a uma liga", description = "Cria a associação entre um time e uma liga. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Liga ou time não encontrado"),
            @ApiResponse(responseCode = "409", description = "Time já pertence a esta liga")
    })
    public ResponseEntity<Object> AddTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        var request = new AddTeamToLeagueRequest(leagueId, teamId);
        var result = this.addTeamToLeagueUseCase.execute(request);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    @Operation(summary = "Lista os times de uma liga", description = "Retorna uma lista com todos os times associados a uma liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    public ResponseEntity<List<ListTeamResponse>> getTeam(@PathVariable UUID leagueId) {
        List<ListTeamResponse> result = listLeagueTeamsUseCase.execute(leagueId);
        return ResponseEntity.ok().body(result);
    }
}

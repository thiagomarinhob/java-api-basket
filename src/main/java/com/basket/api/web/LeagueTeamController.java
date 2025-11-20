package com.basket.api.web;

import com.basket.api.model.useCase.leagueTeam.ListTeamDTO;
import com.basket.api.model.useCase.leagueTeam.AddTeamToLeagueUseCase;
import com.basket.api.model.useCase.leagueTeam.ListLeagueTeamsUseCase;
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


    private final AddTeamToLeagueUseCase addTeamToLeagueUseCase;
    private final ListLeagueTeamsUseCase listLeagueTeamsUseCase;

    public LeagueTeamController(AddTeamToLeagueUseCase addTeamToLeagueUseCase, ListLeagueTeamsUseCase listLeagueTeamsUseCase) {
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
        var result = this.addTeamToLeagueUseCase.execute(leagueId, teamId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    @Operation(summary = "Lista os times de uma liga", description = "Retorna uma lista com todos os times associados a uma liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    public ResponseEntity<List<ListTeamDTO>> getTeam(@PathVariable UUID leagueId) {
        List<ListTeamDTO> result = listLeagueTeamsUseCase.execute(leagueId);
        return ResponseEntity.ok().body(result);
    }
}

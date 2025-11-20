package com.basket.api.web;

import com.basket.api.model.useCase.leagueTeam.ListTeamResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues/{leagueId}/teams")
@Tag(name = "8. Associações (Liga-Time)", description = "Endpoints para associar times a ligas")
@SecurityRequirement(name = "bearer-key")
public interface LeagueTeamAPI {

    @PostMapping("/{teamId}")
    @Operation(summary = "Adiciona um time a uma liga", description = "Cria a associação entre um time e uma liga. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Liga ou time não encontrado"),
            @ApiResponse(responseCode = "409", description = "Time já pertence a esta liga")
    })
    ResponseEntity<Object> addTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId);

    @GetMapping
    @Operation(summary = "Lista os times de uma liga", description = "Retorna uma lista com todos os times associados a uma liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    ResponseEntity<List<ListTeamResponse>> getTeam(@PathVariable UUID leagueId);
}


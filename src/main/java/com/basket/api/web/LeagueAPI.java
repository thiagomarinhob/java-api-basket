package com.basket.api.web;

import com.basket.api.model.useCase.league.LeagueRequest;
import com.basket.api.model.useCase.league.LeagueResponse;
import com.basket.api.model.useCase.league.TeamStandingsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/leagues")
@Tag(name = "Ligas", description = "Endpoints para gerenciamento de ligas")
@SecurityRequirement(name = "bearer-key")
public interface LeagueAPI {

    @PostMapping
    @Operation(summary = "Cria uma nova liga", description = "Cria uma nova liga no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Liga com este nome já existe")
    })
    ResponseEntity<Object> createLeague(@Valid @RequestBody LeagueRequest leagueRequest) throws AuthenticationException;

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma liga pelo ID", description = "Retorna os detalhes de uma liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    ResponseEntity<LeagueResponse> getLeague(@PathVariable UUID id) throws AuthenticationException;

    @GetMapping
    @Operation(summary = "Busca todas as ligas", description = "Retorna todas as ligas cadastradas")
    ResponseEntity<List<LeagueResponse>> getAllLeagues();

    @GetMapping("/{leagueId}/standings")
    @Operation(summary = "Busca a tabela de classificação de uma liga",
            description = "Retorna uma lista ordenada de times com suas respectivas estatísticas (pontos, vitórias, saldo, etc.)")
    ResponseEntity<List<TeamStandingsResponse>> getStandings(@PathVariable UUID leagueId) throws AuthenticationException;
}


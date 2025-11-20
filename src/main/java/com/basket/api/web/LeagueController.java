package com.basket.api.web;

import com.basket.api.model.useCase.league.LeagueRequestDTO;
import com.basket.api.model.useCase.league.LeagueResponseDTO;
import com.basket.api.model.useCase.league.TeamStandingsDTO;
import com.basket.api.model.useCase.league.CreateLeagueUseCase;
import com.basket.api.model.useCase.league.GetLeagueByIdUseCase;
import com.basket.api.model.useCase.league.GetLeagueStandingsUseCase;
import com.basket.api.model.useCase.league.ListLeagueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues")
@Tag(name = "Ligas", description = "Endpoints para gerenciamento de ligas")
@SecurityRequirement(name = "bearer-key")
public class LeagueController {


    private final CreateLeagueUseCase createLeagueUseCase;
    private final GetLeagueByIdUseCase getLeagueByIdUseCase;
    private final ListLeagueUseCase listLeaguesUseCase;
    private final GetLeagueStandingsUseCase getLeagueStandingsUseCase;

    public LeagueController(CreateLeagueUseCase createLeagueUseCase, GetLeagueByIdUseCase getLeagueByIdUseCase, ListLeagueUseCase listLeaguesUseCase, GetLeagueStandingsUseCase getLeagueStandingsUseCase) {
        this.createLeagueUseCase = createLeagueUseCase;
        this.getLeagueByIdUseCase = getLeagueByIdUseCase;
        this.listLeaguesUseCase = listLeaguesUseCase;
        this.getLeagueStandingsUseCase = getLeagueStandingsUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova liga", description = "Cria uma nova liga no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Liga com este nome já existe")
    })
    public ResponseEntity<Object> createLeague(@Valid @RequestBody LeagueRequestDTO leagueRequestDTO) {
        var result = this.createLeagueUseCase.execute(leagueRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma liga pelo ID", description = "Retorna os detalhes de uma liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    public ResponseEntity<LeagueResponseDTO> getLeague(@PathVariable UUID id) {
        LeagueResponseDTO leagueResponse = getLeagueByIdUseCase.execute(id);
        return ResponseEntity.ok(leagueResponse);
    }

    @GetMapping
    @Operation(summary = "Busca todas as ligas", description = "Retorna todas as ligas cadastradas")
    public ResponseEntity<List<LeagueResponseDTO>> getAllLeagues() {
        var leagues = this.listLeaguesUseCase.execute();
        return ResponseEntity.ok(leagues);
    }

    @GetMapping("/{leagueId}/standings")
    @Operation(summary = "Busca a tabela de classificação de uma liga",
            description = "Retorna uma lista ordenada de times com suas respectivas estatísticas (pontos, vitórias, saldo, etc.)")
    public ResponseEntity<List<TeamStandingsDTO>> getStandings(@PathVariable UUID leagueId) {
        List<TeamStandingsDTO> standings = getLeagueStandingsUseCase.execute(leagueId);
        return ResponseEntity.ok(standings);
    }

}

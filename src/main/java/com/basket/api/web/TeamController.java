package com.basket.api.web;

import com.basket.api.model.entity.Team;
import com.basket.api.model.useCase.team.TeamResponseDTO;
import com.basket.api.model.useCase.team.CreateTeamUseCase;
import com.basket.api.model.useCase.team.ListTeamUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teams")
@Tag(name = "Times", description = "Endpoints para gerenciamento de times")
@SecurityRequirement(name = "bearer-key")
public class TeamController {


    private final CreateTeamUseCase createTeamUseCase;
    private final ListTeamUseCase listTeamUseCase;

    public TeamController(CreateTeamUseCase createTeamUseCase, ListTeamUseCase listTeamUseCase) {
        this.createTeamUseCase = createTeamUseCase;
        this.listTeamUseCase = listTeamUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um novo time", description = "Cria um novo time no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Time com este nome já existe")
    })
    public ResponseEntity<Object> createTeam(@Valid @RequestBody Team team) {
        var result = this.createTeamUseCase.execute(team);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um time pelo ID", description = "Retorna os detalhes de um time específico, incluindo suas categorias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    public ResponseEntity<TeamResponseDTO> getTeam(@PathVariable UUID id) {
        var result = this.listTeamUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }
}

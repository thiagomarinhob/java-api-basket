package com.basket.api.web;

import com.basket.api.model.useCase.teamCategory.DefaultAddTeamToCategoryUseCase;
import com.basket.api.model.useCase.teamCategory.DefaultListTeamCategoryUseCase;
import com.basket.api.model.useCase.teamCategory.ListCategoryDTO;
import com.basket.api.model.useCase.teamCategory.TeamCategoryRequestDTO;
import com.basket.api.model.useCase.teamCategory.TeamCategoryResponseDTO;
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
@RequestMapping("/teams/{teamId}/categories")
@Tag(name = "10. Associações (Time-Categoria)", description = "Endpoints para associar categorias a times")
@SecurityRequirement(name = "bearer-key")
public class teamCategoryController {

    private final DefaultAddTeamToCategoryUseCase addTeamToCategoryUseCase;
    private final DefaultListTeamCategoryUseCase listTeamCategoryUseCase;

    public teamCategoryController(DefaultAddTeamToCategoryUseCase addTeamToCategoryUseCase,
                                  DefaultListTeamCategoryUseCase listTeamCategoryUseCase) {
        this.addTeamToCategoryUseCase = addTeamToCategoryUseCase;
        this.listTeamCategoryUseCase = listTeamCategoryUseCase;
    }

    @PostMapping("/add/{categoryId}")
    @Operation(summary = "Adiciona um time a uma categoria", description = "Cria a associação entre um time e uma categoria. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Time ou categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Time já possui esta categoria")
    })
    public ResponseEntity<TeamCategoryResponseDTO> AddTeamToCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        TeamCategoryRequestDTO requestDTO = new TeamCategoryRequestDTO(teamId, categoryId);
        var result = this.addTeamToCategoryUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "Lista as categorias de um time", description = "Retorna uma lista com todas as categorias associadas a um time específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    public ResponseEntity<List<ListCategoryDTO>> getTeam(@PathVariable UUID teamId) {
        List<ListCategoryDTO> result = listTeamCategoryUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }
}

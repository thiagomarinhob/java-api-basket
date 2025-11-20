package com.basket.api.web;

import com.basket.api.model.useCase.teamCategory.ListCategoryResponse;
import com.basket.api.model.useCase.teamCategory.TeamCategoryResponse;
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
@RequestMapping("/teams/{teamId}/categories")
@Tag(name = "10. Associações (Time-Categoria)", description = "Endpoints para associar categorias a times")
@SecurityRequirement(name = "bearer-key")
public interface TeamCategoryAPI {

    @PostMapping("/add/{categoryId}")
    @Operation(summary = "Adiciona um time a uma categoria", description = "Cria a associação entre um time e uma categoria. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Time ou categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Time já possui esta categoria")
    })
    ResponseEntity<TeamCategoryResponse> addTeamToCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId);

    @GetMapping
    @Operation(summary = "Lista as categorias de um time", description = "Retorna uma lista com todas as categorias associadas a um time específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    ResponseEntity<List<ListCategoryResponse>> getTeam(@PathVariable UUID teamId);
}


package com.basket.api.web;

import com.basket.api.model.entity.Team;
import com.basket.api.model.useCase.team.TeamResponse;
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
import java.util.UUID;

@RestController
@RequestMapping("/teams")
@Tag(name = "Times", description = "Endpoints para gerenciamento de times")
@SecurityRequirement(name = "bearer-key")
public interface TeamAPI {

    @PostMapping
    @Operation(summary = "Cria um novo time", description = "Cria um novo time no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Time com este nome já existe")
    })
    ResponseEntity<Object> createTeam(@Valid @RequestBody Team team) throws AuthenticationException;

    @GetMapping("/{id}")
    @Operation(summary = "Busca um time pelo ID", description = "Retorna os detalhes de um time específico, incluindo suas categorias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    ResponseEntity<TeamResponse> getTeam(@PathVariable UUID id) throws AuthenticationException;
}


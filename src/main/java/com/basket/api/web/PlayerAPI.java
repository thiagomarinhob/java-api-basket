package com.basket.api.web;

import com.basket.api.domain.entity.Player;
import com.basket.api.domain.entity.PlayerPosition;
import com.basket.api.domain.useCase.player.PlayerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
@Tag(name = "Jogadores", description = "Endpoints para gerenciamento de jogadores")
@SecurityRequirement(name = "bearer-key")
public interface PlayerAPI {

    @PostMapping
    @Operation(summary = "Cria um novo jogador", description = "Cria um novo jogador no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jogador criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Jogador com este documento já existe")
    })
    ResponseEntity<Object> create(@Valid @RequestBody Player player) throws AuthenticationException;

    @GetMapping
    @Operation(summary = "Lista todos os jogadores", description = "Retorna uma lista paginada de todos os jogadores cadastrados. Use os parâmetros 'page' (começando em 0) e 'size' para navegar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<Page<PlayerResponse>> getAllPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @Operation(summary = "Busca um jogador pelo ID", description = "Retorna os detalhes de um jogador específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Jogador não encontrado")
    })
    ResponseEntity<PlayerResponse> getPlayer(@PathVariable UUID id) throws AuthenticationException;

    @GetMapping("/positions")
    @Operation(summary = "Lista todas as posições disponíveis", description = "Retorna todas as posições de jogador disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de posições retornada com sucesso")
    })
    ResponseEntity<List<PlayerPosition>> getPositions();
}


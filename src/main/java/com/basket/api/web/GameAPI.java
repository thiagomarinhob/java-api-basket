package com.basket.api.web;

import com.basket.api.domain.useCase.game.GameRequest;
import com.basket.api.domain.useCase.game.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/games")
@Tag(name = "Jogos", description = "Endpoints para agendamento e finalização de jogos")
@SecurityRequirement(name = "bearer-key")
public interface GameAPI {

    @PostMapping
    @Operation(summary = "Agenda um novo jogo", description = "Cria um novo jogo com o status 'SCHEDULED'. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jogo agendado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: time da casa igual ao visitante)"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Liga ou time não encontrado")
    })
    ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) throws AuthenticationException;

    @GetMapping("/league/{leagueId}")
    @Operation(summary = "Lista todos os jogos de uma liga", description = "Retorna uma lista de jogos para um ID de liga específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    ResponseEntity<List<GameResponse>> listGamesByLeague(@PathVariable UUID leagueId) throws AuthenticationException;
}


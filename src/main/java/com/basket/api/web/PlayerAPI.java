package com.basket.api.web;

import com.basket.api.domain.entity.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

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
    Object create(@Valid @RequestBody Player player) throws AuthenticationException;
}


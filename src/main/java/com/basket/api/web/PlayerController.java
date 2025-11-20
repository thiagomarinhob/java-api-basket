package com.basket.api.web;

import com.basket.api.domain.entity.Player;
import com.basket.api.domain.useCase.player.CreatePlayerUseCase;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerController implements PlayerAPI {

    private final CreatePlayerUseCase createPlayerUseCase;

    @Override
    public ResponseEntity<Object> create(@Valid @RequestBody Player player) throws AuthenticationException {
        var result = createPlayerUseCase.execute(player);
        return ResponseEntity.ok().body(result);
    }
}

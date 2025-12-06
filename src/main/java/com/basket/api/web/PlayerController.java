package com.basket.api.web;

import com.basket.api.domain.entity.Player;
import com.basket.api.domain.useCase.player.CreatePlayerUseCase;
import com.basket.api.domain.useCase.player.GetPlayerByIdUseCase;
import com.basket.api.domain.useCase.player.ListPlayerUseCase;
import com.basket.api.domain.useCase.player.PlayerResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerController implements PlayerAPI {

    private final CreatePlayerUseCase createPlayerUseCase;
    private final ListPlayerUseCase listPlayerUseCase;
    private final GetPlayerByIdUseCase getPlayerByIdUseCase;

    @Override
    public ResponseEntity<Object> create(@Valid @RequestBody Player player) throws AuthenticationException {
        var result = createPlayerUseCase.execute(player);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Page<PlayerResponse>> getAllPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PlayerResponse> playersPage = listPlayerUseCase.execute(pageRequest);
        return ResponseEntity.ok(playersPage);
    }

    @Override
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable UUID id) throws AuthenticationException {
        PlayerResponse player = getPlayerByIdUseCase.execute(id);
        return ResponseEntity.ok(player);
    }

    @Override
    public ResponseEntity<List<com.basket.api.domain.entity.PlayerPosition>> getPositions() {
        List<com.basket.api.domain.entity.PlayerPosition> positions = Arrays.asList(
                com.basket.api.domain.entity.PlayerPosition.values()
        );
        return ResponseEntity.ok(positions);
    }
}

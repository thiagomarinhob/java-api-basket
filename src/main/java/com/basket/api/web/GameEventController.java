package com.basket.api.web;

import com.basket.api.domain.useCase.gameEvent.AddGameEventUseCase;
import com.basket.api.domain.useCase.gameEvent.GameEventRequest;
import com.basket.api.domain.useCase.gameEvent.GameEventResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GameEventController implements GameEventAPI {

    private final AddGameEventUseCase addGameEventUseCase;

    @Override
    public ResponseEntity<GameEventResponse> createGameEvent(@RequestBody GameEventRequest request) throws AuthenticationException {
        GameEventResponse event = addGameEventUseCase.execute(request);
        return ResponseEntity.ok(event);
    }
}

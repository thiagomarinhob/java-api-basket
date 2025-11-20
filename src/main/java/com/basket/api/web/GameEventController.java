package com.basket.api.web;

import com.basket.api.model.useCase.gameEvent.GameEventRequestDTO;
import com.basket.api.model.entity.GameEvent;
import com.basket.api.model.useCase.gameEvent.AddGameEventUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games/events")
public class GameEventController {

    private final AddGameEventUseCase addGameEventUseCase;

    public GameEventController(AddGameEventUseCase addGameEventUseCase) {
        this.addGameEventUseCase = addGameEventUseCase;
    }

    @PostMapping
    public ResponseEntity<GameEvent> createGameEvent(@RequestBody GameEventRequestDTO request) {
        GameEvent event = addGameEventUseCase.execute(request);
        return ResponseEntity.ok(event);
    }
}

package com.basket.api.web;

import com.basket.api.domain.entity.GameEvent;
import com.basket.api.domain.useCase.gameEvent.GameEventRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/games/events")
public interface GameEventAPI {

    @PostMapping
    ResponseEntity<GameEvent> createGameEvent(@RequestBody GameEventRequest request) throws AuthenticationException;
}


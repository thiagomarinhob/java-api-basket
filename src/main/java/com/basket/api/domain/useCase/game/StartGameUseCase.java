package com.basket.api.domain.useCase.game;

public interface StartGameUseCase {
    GameResponse execute(java.util.UUID gameId) throws javax.naming.AuthenticationException;
}


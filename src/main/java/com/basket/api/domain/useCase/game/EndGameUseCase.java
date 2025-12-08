package com.basket.api.domain.useCase.game;

public interface EndGameUseCase {
    GameResponse execute(java.util.UUID gameId) throws javax.naming.AuthenticationException;
}


package com.basket.api.domain.useCase.gameEvent;

import java.util.UUID;

public record GameEventPlayerResponse(UUID id, String firstName, String lastName, String nickName) {}


package com.basket.api.model.useCase.game;

import java.util.UUID;

public record GameTeamResponseDTO(UUID id, String name, String logoUrl) {}
package com.basket.api.domain.useCase.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ListPlayerUseCase {
    Page<PlayerResponse> execute(PageRequest pageRequest);
}


package com.basket.api.domain.useCase.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ListTeamUseCase {
    Page<TeamResponse> execute(PageRequest pageRequest);
}

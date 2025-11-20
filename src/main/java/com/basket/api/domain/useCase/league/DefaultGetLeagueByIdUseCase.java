package com.basket.api.domain.useCase.league;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.repository.LeagueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetLeagueByIdUseCase implements GetLeagueByIdUseCase {

    private final LeagueRepository leagueRepository;

    @Override
    public LeagueResponse execute(UUID id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga não encontrada com o ID: " + id));

        return new LeagueResponse(
                league.getId(),
                league.getName(),
                league.getDescription(),
                league.getLogoUrl(),
                league.getStartDate() != null ? new java.sql.Date(league.getStartDate().getTime()).toLocalDate() : null,
                league.getEndDate() != null ? new java.sql.Date(league.getEndDate().getTime()).toLocalDate() : null
        );
    }
}

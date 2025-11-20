package com.basket.api.model.useCase.league;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.League;
import com.basket.api.model.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLeagueByIdUseCase {


    private final LeagueRepository leagueRepository;

    public GetLeagueByIdUseCase(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public LeagueResponseDTO execute(UUID id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga não encontrada com o ID: " + id));

        return new LeagueResponseDTO(
                league.getId(),
                league.getName(),
                league.getDescription(),
                league.getLogoUrl(),
                league.getStartDate() != null ? new java.sql.Date(league.getStartDate().getTime()).toLocalDate() : null,
                league.getEndDate() != null ? new java.sql.Date(league.getEndDate().getTime()).toLocalDate() : null
        );
    }
}

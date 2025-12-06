package com.basket.api.domain.useCase.league;

import com.basket.api.domain.useCase.category.CategoryResponse;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.entity.Category;
import com.basket.api.domain.repository.LeagueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListLeagueUseCase implements ListLeagueUseCase {

    private final LeagueRepository leagueRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LeagueResponse> execute() {
        List<League> leagues = leagueRepository.findAll();

        return leagues.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private LeagueResponse convertToResponse(League league) {
        Category category = league.getCategory();
        CategoryResponse categoryResponse = category != null
                ? new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getCategoryGender())
                : null;

        return new LeagueResponse(
                league.getId(),
                league.getName(),
                league.getDescription(),
                league.getLogoUrl(),
                league.getStartDate() != null ? new java.sql.Date(league.getStartDate().getTime()).toLocalDate() : null,
                league.getEndDate() != null ? new java.sql.Date(league.getEndDate().getTime()).toLocalDate() : null,
                league.getMinTeams(),
                league.getMaxTeams(),
                categoryResponse
        );
    }
}

package com.basket.api.domain.useCase.league;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.repository.CategoryRepository;
import com.basket.api.domain.repository.LeagueRepository;
import com.basket.api.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreateLeagueUseCase implements CreateLeagueUseCase {

    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public LeagueResponse execute(LeagueRequest leagueRequest) {
        if (leagueRepository.findByName(leagueRequest.name()).isPresent()) {
            throw new BusinessRuleException("League with name '" + leagueRequest.name() + "' already exists.");
        }

        final var user = userRepository.findById(leagueRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        final var category = categoryRepository.findById(leagueRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        League league = new League();
        league.setName(leagueRequest.name());
        league.setDescription(leagueRequest.description());
        league.setLogoUrl(leagueRequest.logoUrl());

        league.setUser(user);
        league.setCategory(category);

        final var saveLeague = leagueRepository.save(league);

        return LeagueResponse.builder()
                .name(saveLeague.getName())
                .description(saveLeague.getDescription())
                .build();
    }
}

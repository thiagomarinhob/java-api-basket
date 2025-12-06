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
    public League execute(LeagueRequest leagueRequest) {
        if (leagueRepository.findByName(leagueRequest.name()).isPresent()) {
            throw new BusinessRuleException("League with name '" + leagueRequest.name() + "' already exists.");
        }

        var user = userRepository.findById(leagueRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var category = categoryRepository.findById(leagueRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        // Validações de quantidade de times
        Integer minTeams = leagueRequest.minTeams();
        Integer maxTeams = leagueRequest.maxTeams();
        
        if (minTeams != null && minTeams < 0) {
            throw new BusinessRuleException("A quantidade mínima de times não pode ser negativa.");
        }
        
        if (maxTeams != null && maxTeams < 0) {
            throw new BusinessRuleException("A quantidade máxima de times não pode ser negativa.");
        }
        
        if (minTeams != null && maxTeams != null && minTeams > maxTeams) {
            throw new BusinessRuleException("A quantidade mínima de times não pode ser maior que a quantidade máxima.");
        }

        League league = new League();
        league.setName(leagueRequest.name());
        league.setDescription(leagueRequest.description());
        league.setLogoUrl(leagueRequest.logoUrl());
        league.setMinTeams(minTeams);
        league.setMaxTeams(maxTeams);

        league.setUser(user);
        league.setCategory(category);

        return leagueRepository.save(league);
    }
}

package com.basket.api.model.useCase.league;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.repository.CategoryRepository;
import com.basket.api.model.entity.League;
import com.basket.api.model.repository.LeagueRepository;
import com.basket.api.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateLeagueUseCase {


    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CreateLeagueUseCase(LeagueRepository leagueRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public League execute(LeagueRequestDTO leagueRequestDTO) {
        if (leagueRepository.findByName(leagueRequestDTO.name()).isPresent()) {
            throw new BusinessRuleException("League with name '" + leagueRequestDTO.name() + "' already exists.");
        }

        var user = userRepository.findById(leagueRequestDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var category = categoryRepository.findById(leagueRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        League league = new League();
        league.setName(leagueRequestDTO.name());
        league.setDescription(leagueRequestDTO.description());
        league.setLogoUrl(leagueRequestDTO.logoUrl());

        league.setUser(user);
        league.setCategory(category);

        return leagueRepository.save(league);
    }
}

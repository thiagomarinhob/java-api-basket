package com.basket.api.domain.useCase.teamCategory;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Category;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.TeamCategory;
import com.basket.api.domain.repository.CategoryRepository;
import com.basket.api.domain.repository.TeamCategoryRepository;
import com.basket.api.domain.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultAddTeamToCategoryUseCase implements AddTeamToCategoryUseCase {

    private final TeamCategoryRepository teamCategoryRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public TeamCategoryResponse execute(TeamCategoryRequest teamCategoryRequest) {
        Team team = teamRepository.findById(teamCategoryRequest.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Time não existe"));

        Category category = categoryRepository.findById(teamCategoryRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não existe"));

        boolean exists = teamCategoryRepository.existsByTeamAndCategory(team, category);
        if (exists) {
            throw new BusinessRuleException("Ja existe um time com essa categoria");
        }

        TeamCategory teamCategory = new TeamCategory();
        teamCategory.setCategory(category);
        teamCategory.setTeam(team);

        TeamCategory savedEntity = teamCategoryRepository.save(teamCategory);

        return new TeamCategoryResponse(
                savedEntity.getId(),
                savedEntity.getTeam().getId(),
                savedEntity.getCategory().getId(),
                savedEntity.getTeam().getName(),
                savedEntity.getCategory().getName()
        );
    }
}

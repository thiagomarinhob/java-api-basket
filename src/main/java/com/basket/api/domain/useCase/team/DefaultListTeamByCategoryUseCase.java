package com.basket.api.domain.useCase.team;

import com.basket.api.domain.useCase.category.CategoryResponse;
import com.basket.api.domain.entity.TeamCategory;
import com.basket.api.domain.repository.TeamCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListTeamByCategoryUseCase implements ListTeamByCategoryUseCase {

    private final TeamCategoryRepository teamCategoryRepository;

    @Override
    public List<TeamResponse> execute(UUID categoryId) {
        List<TeamCategory> teamCategories = teamCategoryRepository.findByCategoryId(categoryId);

        return teamCategories.stream()
                .map(teamCategory -> {
                    var team = teamCategory.getTeam();
                    List<CategoryResponse> categoryResponses = team.getCategoryEntityList()
                            .stream()
                            .map(category -> new CategoryResponse(
                                    category.getCategory().getId(),
                                    category.getCategory().getName(),
                                    category.getCategory().getDescription(),
                                    category.getCategory().getCategoryGender()
                            ))
                            .toList();

                    return new TeamResponse(
                            team.getId(),
                            team.getName(),
                            team.getShortName(),
                            team.getLogoUrl(),
                            team.getLocation(),
                            team.getDescription(),
                            team.getRanking(),
                            categoryResponses
                    );
                })
                .toList();
    }
}


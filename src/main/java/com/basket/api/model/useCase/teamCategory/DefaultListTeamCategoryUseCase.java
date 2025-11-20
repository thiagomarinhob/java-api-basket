package com.basket.api.model.useCase.teamCategory;

import com.basket.api.model.entity.TeamCategory;
import com.basket.api.model.repository.TeamCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListTeamCategoryUseCase implements ListTeamCategoryUseCase {

    private final TeamCategoryRepository teamCategoryRepository;

    @Override
    public List<ListCategoryResponse> execute(UUID teamId) {
        List<TeamCategory> listTeamCategories = this.teamCategoryRepository.findByTeamId(teamId);

        return listTeamCategories.stream()
                .map(teamCategory -> new ListCategoryResponse(
                        teamCategory.getCategory().getId(),
                        teamCategory.getCategory().getName(),
                        teamCategory.getCategory().getCategoryGender()
                ))
                .toList();
    }
}

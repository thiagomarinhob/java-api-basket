package com.basket.api.domain.useCase.team;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.useCase.category.CategoryResponse;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetTeamByIdUseCase implements GetTeamByIdUseCase {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponse execute(UUID id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));

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
    }
}


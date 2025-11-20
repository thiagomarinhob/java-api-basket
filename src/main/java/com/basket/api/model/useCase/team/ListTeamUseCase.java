package com.basket.api.model.useCase.team;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.useCase.category.CategoryResponseDTO;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTeamUseCase {


    private final TeamRepository teamRepository;

    public ListTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamResponseDTO execute(UUID id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));

        List<CategoryResponseDTO> categoryDTOs = team.getCategoryEntityList()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getCategory().getId(),
                        category.getCategory().getName(),
                        category.getCategory().getDescription(),
                        category.getCategory().getCategoryGender()
                ))
                .toList();

        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getShortName(),
                team.getLogoUrl(),
                team.getLocation(),
                team.getDescription(),
                team.getRanking(),
                categoryDTOs
        );
    }
}

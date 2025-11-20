package com.basket.api.domain.useCase.teamPlayer;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.Category;
import com.basket.api.domain.entity.Player;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.TeamPlayer;
import com.basket.api.domain.repository.CategoryRepository;
import com.basket.api.domain.repository.PlayerRepository;
import com.basket.api.domain.repository.TeamCategoryRepository;
import com.basket.api.domain.repository.TeamPlayerRepository;
import com.basket.api.domain.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultAddTeamPlayerUseCase implements AddTeamPlayerUseCase {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamCategoryRepository teamCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public TeamPlayerResponse execute(TeamPlayerRequest teamPlayerRequest) {

        Team destinationTeam = teamRepository.findById(teamPlayerRequest.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Player player = playerRepository.findById(teamPlayerRequest.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        Category category = categoryRepository.findById(teamPlayerRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        boolean teamHasCategory = teamCategoryRepository.existsByTeamAndCategory(destinationTeam, category);

        if (!teamHasCategory) {
            throw new BusinessRuleException(
                    "Não é possível adicionar o jogador. O time '" + destinationTeam.getName() +
                            "' não está registrado na categoria '" + category.getName() + "'.");
        }

        Optional<TeamPlayer> existingActiveAssociation = teamPlayerRepository
                .findByPlayerAndCategoryAndIsActive(player, category, true);

        if (existingActiveAssociation.isPresent()) {
            Team existingTeam = existingActiveAssociation.get().getTeam();
            if (existingTeam.getId().equals(destinationTeam.getId())) {
                throw new BusinessRuleException(
                        "O jogador já está ativo nesta equipe (Time: " + destinationTeam.getName() +
                                ", Categoria: " + category.getName() + ").");
            } else {
                throw new BusinessRuleException(
                        "Este jogador já está ativo na categoria '" + category.getName() +
                                "' pelo time '" + existingTeam.getName() + "'.");
            }
        }

        TeamPlayer newAssociation = new TeamPlayer();
        newAssociation.setTeam(destinationTeam);
        newAssociation.setPlayer(player);
        newAssociation.setCategory(category);
        newAssociation.setIsActive(true);
        newAssociation.setStartDate(LocalDateTime.now());

        TeamPlayer savedAssociation = teamPlayerRepository.save(newAssociation);

        return new TeamPlayerResponse(
                savedAssociation.getId(),
                savedAssociation.getTeam().getId(),
                savedAssociation.getTeam().getName(),
                savedAssociation.getPlayer().getId(),
                savedAssociation.getPlayer().getFirstName() + " " + savedAssociation.getPlayer().getLastName(),
                savedAssociation.getCategory().getId(),
                savedAssociation.getCategory().getName(),
                savedAssociation.getIsActive(),
                savedAssociation.getStartDate()
        );
    }
}

package com.basket.api.model.useCase.stats;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.Category;
import com.basket.api.model.entity.Game;
import com.basket.api.model.repository.GameRepository;
import com.basket.api.model.repository.TeamPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ValidateGameStatsUseCase {

    private final GameRepository gameRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    public ValidateGameStatsUseCase(GameRepository gameRepository, TeamPlayerRepository teamPlayerRepository) {
        this.gameRepository = gameRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public void execute(UUID gameId, List<PlayerStatsRequest> playerStatsList) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        Category leagueCategory = game.getLeague().getCategory();

        for (PlayerStatsRequest statsDTO : playerStatsList) {
            teamPlayerRepository.findByTeamIdAndPlayerIdAndCategoryIdAndIsActive(
                            statsDTO.teamId(), statsDTO.playerId(), leagueCategory.getId(), true)
                    .orElseThrow(() -> new BusinessRuleException(
                            "Validação falhou: O jogador com ID " + statsDTO.playerId() +
                                    " não possui uma associação ativa com o time ID " + statsDTO.teamId() +
                                    " na categoria da liga ('" + leagueCategory.getName() + "')."));
        }
    }
}

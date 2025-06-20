package com.basket.api.modules.TeamPlayer.useCases;

import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.repository.TeamRepository;
import com.basket.api.modules.TeamPlayer.dto.TeamPlayerRequestDTO;
import com.basket.api.modules.TeamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.TeamPlayer.repository.TeamPlayerRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddTeamPlayerUseCase {

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public TeamPlayerEntity execute(TeamPlayerRequestDTO teamPlayerRequestDTO) {
        TeamEntity team = teamRepository.findById(teamPlayerRequestDTO.teamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        PlayerEntity player = playerRepository.findById(teamPlayerRequestDTO.playerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Optional<TeamPlayerEntity> existingAssociation = teamPlayerRepository.findByTeamAndPlayerAndIsActive(team, player, true);
        if (existingAssociation.isPresent()) {
            throw new RuntimeException("Player is already in this team");
        }

        teamPlayerRepository.desactivatePreviousAssociations(player);

        TeamPlayerEntity newAssociation = new TeamPlayerEntity();
        newAssociation.setTeam(team);
        newAssociation.setPlayer(player);
        newAssociation.setIsActive(true);
        newAssociation.setStartDate(LocalDateTime.now());

        return teamPlayerRepository.save(newAssociation);
    }
}

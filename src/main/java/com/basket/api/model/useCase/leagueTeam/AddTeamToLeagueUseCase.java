package com.basket.api.model.useCase.leagueTeam;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.model.entity.LeagueTeam;
import com.basket.api.model.entity.TeamStatus;
import com.basket.api.model.repository.LeagueTeamRepository;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.TeamRepository;
import com.basket.api.model.entity.League;
import com.basket.api.model.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddTeamToLeagueUseCase {

    private final LeagueTeamRepository leagueTeamRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public AddTeamToLeagueUseCase(LeagueTeamRepository leagueTeamRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public LeagueTeamResponseDTO execute(UUID leagueId, UUID teamId) {
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League não existe"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Time não existe"));

        boolean exists = leagueTeamRepository.existsByLeagueAndTeam(league, team);
        if (exists) {
            throw new BusinessRuleException("Team already added to this league");
        }

        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeamStatus(TeamStatus.ACTIVE);
        leagueTeam.setLeague(league);
        leagueTeam.setTeam(team);

        LeagueTeam savedAssociation = leagueTeamRepository.save(leagueTeam);

        return new LeagueTeamResponseDTO(
                savedAssociation.getId(),
                savedAssociation.getLeague().getId(),
                savedAssociation.getLeague().getName(),
                savedAssociation.getTeam().getId(),
                savedAssociation.getTeam().getName(),
                savedAssociation.getCreatedAt()
        );
    }
}

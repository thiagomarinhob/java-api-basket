package com.basket.api.domain.useCase.leagueTeam;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.entity.LeagueTeam;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.TeamStatus;
import com.basket.api.domain.repository.LeagueRepository;
import com.basket.api.domain.repository.LeagueTeamRepository;
import com.basket.api.domain.repository.TeamRepository;
import com.basket.api.domain.repository.TeamCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultAddTeamToLeagueUseCase implements AddTeamToLeagueUseCase {

    private final LeagueTeamRepository leagueTeamRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final TeamCategoryRepository teamCategoryRepository;

    @Override
    public LeagueTeamResponse execute(AddTeamToLeagueRequest request) {
        League league = leagueRepository.findById(request.leagueId())
                .orElseThrow(() -> new ResourceNotFoundException("League não existe"));

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Time não existe"));

        boolean exists = leagueTeamRepository.existsByLeagueAndTeam(league, team);
        if (exists) {
            throw new BusinessRuleException("Team already added to this league");
        }

        // Valida se o time tem a mesma categoria da liga
        boolean teamHasLeagueCategory = teamCategoryRepository.existsByTeamAndCategory(team, league.getCategory());
        if (!teamHasLeagueCategory) {
            throw new BusinessRuleException(
                String.format("O time '%s' não possui a categoria '%s' da liga. É necessário que o time tenha a mesma categoria da liga.", 
                    team.getName(), league.getCategory().getName())
            );
        }

        // Valida quantidade máxima de times
        if (league.getMaxTeams() != null) {
            long currentTeamCount = leagueTeamRepository.countByLeague(league);
            if (currentTeamCount >= league.getMaxTeams()) {
                throw new BusinessRuleException(
                    String.format("A liga já atingiu o número máximo de times permitido (%d). Não é possível adicionar mais times.", 
                        league.getMaxTeams())
                );
            }
        }

        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeamStatus(TeamStatus.ACTIVE);
        leagueTeam.setLeague(league);
        leagueTeam.setTeam(team);

        LeagueTeam savedAssociation = leagueTeamRepository.save(leagueTeam);

        return new LeagueTeamResponse(
                savedAssociation.getId(),
                savedAssociation.getLeague().getId(),
                savedAssociation.getLeague().getName(),
                savedAssociation.getTeam().getId(),
                savedAssociation.getTeam().getName(),
                savedAssociation.getCreatedAt()
        );
    }
}

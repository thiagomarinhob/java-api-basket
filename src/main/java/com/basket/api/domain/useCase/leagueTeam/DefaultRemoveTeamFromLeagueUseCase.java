package com.basket.api.domain.useCase.leagueTeam;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.domain.entity.League;
import com.basket.api.domain.entity.LeagueTeam;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.repository.LeagueRepository;
import com.basket.api.domain.repository.LeagueTeamRepository;
import com.basket.api.domain.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultRemoveTeamFromLeagueUseCase implements RemoveTeamFromLeagueUseCase {

    private final LeagueTeamRepository leagueTeamRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    @Override
    public Void execute(AddTeamToLeagueRequest request) {
        League league = leagueRepository.findById(request.leagueId())
                .orElseThrow(() -> new ResourceNotFoundException("League não existe"));

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Time não existe"));

        LeagueTeam leagueTeam = leagueTeamRepository.findByLeagueAndTeam(league, team)
                .orElseThrow(() -> new ResourceNotFoundException("Time não está associado a esta liga"));

        leagueTeamRepository.delete(leagueTeam);
        
        return null;
    }
}


package com.basket.api.model.useCase.leagueTeam;

import com.basket.api.model.entity.LeagueTeam;
import com.basket.api.model.repository.LeagueTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListLeagueTeamsUseCase {


    private final LeagueTeamRepository leagueTeamRepository;

    public ListLeagueTeamsUseCase(LeagueTeamRepository leagueTeamRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
    }

    public List<ListTeamDTO> execute(UUID leagueId) {
        List<LeagueTeam> listLeagueTeams = this.leagueTeamRepository.findByLeagueId(leagueId);

        return listLeagueTeams.stream()
                .map(leagueTeam -> new ListTeamDTO(
                                leagueTeam.getTeam().getId(),
                                leagueTeam.getTeam().getName(),
                                leagueTeam.getTeam().getLogoUrl(),
                                leagueTeam.getTeam().getLocation()
                        )
                ).toList();
    }
}

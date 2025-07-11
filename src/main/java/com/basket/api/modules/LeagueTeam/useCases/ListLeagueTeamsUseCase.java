package com.basket.api.modules.LeagueTeam.useCases;

import com.basket.api.modules.LeagueTeam.dto.ListTeamDTO;
import com.basket.api.modules.LeagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.LeagueTeam.repository.LeagueTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListLeagueTeamsUseCase {

    @Autowired
    private LeagueTeamRepository leagueTeamRepository;

    public List<ListTeamDTO> execute(UUID leagueId) {
        List<LeagueTeamEntity> listLeagueTeams = this.leagueTeamRepository.findByLeagueId(leagueId);

        return listLeagueTeams.stream()
                .map(leagueTeam -> ListTeamDTO.builder()
                        .id(leagueTeam.getTeam().getId())
                        .name(leagueTeam.getTeam().getName())
                        .logoUrl(leagueTeam.getTeam().getLogoUrl())
                        .location(leagueTeam.getTeam().getLocation())
                        .build()
                ).toList();
    }
}

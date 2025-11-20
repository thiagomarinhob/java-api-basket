package com.basket.api.model.useCase.leagueTeam;

import com.basket.api.model.entity.LeagueTeam;
import com.basket.api.model.repository.LeagueTeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListLeagueTeamsUseCase implements ListLeagueTeamsUseCase {

    private final LeagueTeamRepository leagueTeamRepository;

    @Override
    public List<ListTeamResponse> execute(UUID leagueId) {
        List<LeagueTeam> listLeagueTeams = this.leagueTeamRepository.findByLeagueId(leagueId);

        return listLeagueTeams.stream()
                .map(leagueTeam -> new ListTeamResponse(
                                leagueTeam.getTeam().getId(),
                                leagueTeam.getTeam().getName(),
                                leagueTeam.getTeam().getLogoUrl(),
                                leagueTeam.getTeam().getLocation()
                        )
                ).toList();
    }
}

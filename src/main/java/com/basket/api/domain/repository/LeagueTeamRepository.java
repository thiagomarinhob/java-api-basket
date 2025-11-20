package com.basket.api.domain.repository;

import com.basket.api.domain.entity.LeagueTeam;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeagueTeamRepository extends JpaRepository<LeagueTeam, UUID> {
    boolean existsByLeagueAndTeam(League league, Team team);
    List<LeagueTeam> findByLeagueId(UUID leagueId);

}

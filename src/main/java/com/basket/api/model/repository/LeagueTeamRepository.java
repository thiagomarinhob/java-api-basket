package com.basket.api.model.repository;

import com.basket.api.model.entity.LeagueTeam;
import com.basket.api.model.entity.Team;
import com.basket.api.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeagueTeamRepository extends JpaRepository<LeagueTeam, UUID> {
    boolean existsByLeagueAndTeam(League league, Team team);
    List<LeagueTeam> findByLeagueId(UUID leagueId);

}

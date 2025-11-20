package com.basket.api.domain.repository;

import com.basket.api.domain.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League, UUID> {
    Optional<League> findByName(String name);
}

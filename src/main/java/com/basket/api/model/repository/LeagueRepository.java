package com.basket.api.model.repository;

import com.basket.api.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League, UUID> {
    Optional<League> findByName(String name);
}

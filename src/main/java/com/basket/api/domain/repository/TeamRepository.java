package com.basket.api.domain.repository;

import com.basket.api.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String name);

    @Query("SELECT DISTINCT t FROM team t LEFT JOIN FETCH t.categoryEntityList tc LEFT JOIN FETCH tc.category WHERE t.id = :id")
    Optional<Team> findByIdWithCategories(@Param("id") UUID id);
}

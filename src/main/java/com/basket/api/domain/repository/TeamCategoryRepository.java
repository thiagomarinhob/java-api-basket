package com.basket.api.domain.repository;

import com.basket.api.domain.entity.Category;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.TeamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamCategoryRepository extends JpaRepository<TeamCategory, UUID> {
    boolean existsByTeamAndCategory(Team team, Category category);
    List<TeamCategory> findByTeamId(UUID teamId);

}

package com.basket.api.domain.repository;

import com.basket.api.domain.entity.Category;
import com.basket.api.domain.entity.Team;
import com.basket.api.domain.entity.TeamPlayer;
import com.basket.api.domain.entity.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, UUID> {
    Optional<TeamPlayer> findByTeamAndPlayerAndIsActive(Team team, Player player, Boolean isActive);
    List<TeamPlayer> findByTeamIdAndIsActive(UUID teamId, Boolean isActive);

    List<TeamPlayer> findByTeamIdAndCategoryIdAndIsActive(UUID teamId, UUID categoryId, Boolean isActive);

    Optional<TeamPlayer> findByTeamIdAndPlayerIdAndCategoryIdAndIsActive(UUID teamId, UUID playerId, UUID categoryId, boolean isActive);

    List<TeamPlayer> findByPlayerAndIsActive(Player player, Boolean isActive);

    Optional<TeamPlayer> findByPlayerAndCategoryAndIsActive(Player player, Category category, Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE team_player tp SET tp.isActive = false, tp.endDate = CURRENT_TIMESTAMP WHERE tp.player = :player AND tp.isActive = true")
    void desactivatePreviousAssociations(@Param("player") Player player);

}

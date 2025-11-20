package com.basket.api.domain.repository;

import com.basket.api.domain.entity.GameEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameEventRepository extends JpaRepository<GameEvent, UUID> {
    List<GameEvent> findByGameId(UUID gameId);
}
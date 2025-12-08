package com.basket.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "game_events")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Player player;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private GameEventType eventType;

    @Column(name = "event_time", nullable = false)
    private Integer eventTime; // Tempo decorrido no jogo, em segundos.

    @Column(name = "points", nullable = true)
    private Integer points; // Número de pontos (para cestas).

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

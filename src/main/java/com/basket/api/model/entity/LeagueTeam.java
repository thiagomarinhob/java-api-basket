package com.basket.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "league_team")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"league_id", "team_id"}, name = "uk_league_team")
})
public class LeagueTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private TeamStatus  teamStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

package com.basket.api.domain.entity;

import com.basket.api.domain.useCase.gameEvent.GameEventRequest;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity(name = "player_game_stats")
public class PlayerGameStats {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private Integer points1 = 0;
    private Integer points2 = 0;
    private Integer points3 = 0;
    private Integer fouls = 0;
    private Integer assists = 0;
    private Integer rebounds = 0;

    public static PlayerGameStats newFor(Game game, Team team, Player player) {
        PlayerGameStats stats = new PlayerGameStats();
        stats.setGame(game);
        stats.setTeam(team);
        stats.setPlayer(player);

        return stats;
    }

    public void registerEvent(GameEventRequest request) {
        switch (request.eventType()) {
            case POINTS -> registerPoints(request.points());
            case FOUL -> this.fouls++;
            case ASSIST -> this.assists++;
            case REBOUND -> this.rebounds++;

            default -> throw new IllegalArgumentException("Unsupported event type");
        }
    }

    private void registerPoints(Integer rawPoints) {
        int points = rawPoints != null ? rawPoints : 0;
        if (points == 1) this.points1++;
        else if (points == 2) this.points2++;
        else if (points == 3) this.points3++;
    }
}
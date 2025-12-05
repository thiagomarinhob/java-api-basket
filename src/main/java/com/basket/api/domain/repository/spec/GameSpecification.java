package com.basket.api.domain.repository.spec;

import com.basket.api.domain.entity.Game;
import com.basket.api.domain.entity.GameStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSpecification {

    public static Specification<Game> withFilters(UUID leagueId, GameStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Liga (Opcional)
            if (leagueId != null) {
                predicates.add(criteriaBuilder.equal(root.get("league").get("id"), leagueId));
            }

            // Filtro por Status (Opcional)
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filtro por Intervalo de Data (Opcional)
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scheduledDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scheduledDate"), endDate));
            }

            // Ordenação padrão (ex: data do jogo descendente) se não vier no Pageable
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                query.orderBy(criteriaBuilder.desc(root.get("scheduledDate")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
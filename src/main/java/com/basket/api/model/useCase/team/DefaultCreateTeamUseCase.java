package com.basket.api.model.useCase.team;

import com.basket.api.exception.TeamAlreadyExistsException;
import com.basket.api.model.entity.Team;
import com.basket.api.model.repository.TeamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreateTeamUseCase implements CreateTeamUseCase {

    private final TeamRepository teamRepository;

    @Override
    public Team execute(Team teamEntity) {
        teamRepository.findByName(teamEntity.getName()).ifPresent(team -> {
            throw new TeamAlreadyExistsException("Um time com o nome '" + teamEntity.getName() + "' já existe.");
        });

        return teamRepository.save(teamEntity);
    }
}

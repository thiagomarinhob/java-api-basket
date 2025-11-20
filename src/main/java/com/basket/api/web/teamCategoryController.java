package com.basket.api.web;

import com.basket.api.domain.useCase.teamCategory.DefaultAddTeamToCategoryUseCase;
import com.basket.api.domain.useCase.teamCategory.DefaultListTeamCategoryUseCase;
import com.basket.api.domain.useCase.teamCategory.ListCategoryResponse;
import com.basket.api.domain.useCase.teamCategory.TeamCategoryRequest;
import com.basket.api.domain.useCase.teamCategory.TeamCategoryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class teamCategoryController implements TeamCategoryAPI {

    private final DefaultAddTeamToCategoryUseCase addTeamToCategoryUseCase;
    private final DefaultListTeamCategoryUseCase listTeamCategoryUseCase;

    @Override
    public ResponseEntity<TeamCategoryResponse> addTeamToCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        TeamCategoryRequest request = new TeamCategoryRequest(teamId, categoryId);
        var result = this.addTeamToCategoryUseCase.execute(request);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<ListCategoryResponse>> getTeam(@PathVariable UUID teamId) {
        List<ListCategoryResponse> result = listTeamCategoryUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }
}

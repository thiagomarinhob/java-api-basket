package com.basket.api.web;

import com.basket.api.domain.entity.Category;
import com.basket.api.domain.useCase.category.CategoryRequest;
import com.basket.api.domain.useCase.category.CreateCategoryUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    @Override
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) throws AuthenticationException {
        Category category = createCategoryUseCase.execute(categoryRequest);
        return ResponseEntity.ok(category);
    }
}

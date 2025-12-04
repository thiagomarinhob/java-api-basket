package com.basket.api.web;

import com.basket.api.domain.entity.Category;
import com.basket.api.domain.useCase.category.CategoryRequest;
import com.basket.api.domain.useCase.category.CategoryResponse;
import com.basket.api.domain.useCase.category.CreateCategoryUseCase;
import com.basket.api.domain.useCase.category.ListCategoryUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoryUseCase listCategoryUseCase;

    @Override
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) throws AuthenticationException {
        Category category = createCategoryUseCase.execute(categoryRequest);
        return ResponseEntity.ok(category);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = listCategoryUseCase.execute();
        return ResponseEntity.ok(categories);
    }
}

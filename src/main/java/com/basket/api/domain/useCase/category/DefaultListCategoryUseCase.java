package com.basket.api.domain.useCase.category;

import com.basket.api.domain.entity.Category;
import com.basket.api.domain.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListCategoryUseCase implements ListCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> execute() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse convertToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCategoryGender()
        );
    }
}


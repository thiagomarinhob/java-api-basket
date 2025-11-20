package com.basket.api.model.useCase.category;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.model.entity.Category;
import com.basket.api.model.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreateCategoryUseCase implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public Category execute(CategoryRequest categoryRequest) {
        if (categoryRepository.findByName(categoryRequest.name()).isPresent()) {
            throw new BusinessRuleException("Categoria não existe");
        }

        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        category.setCategoryGender(categoryRequest.categoryGender());

        return categoryRepository.save(category);
    }
}

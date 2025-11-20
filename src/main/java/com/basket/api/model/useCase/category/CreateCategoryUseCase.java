package com.basket.api.model.useCase.category;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.model.entity.Category;
import com.basket.api.model.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCase {

    public final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.findByName(categoryRequestDTO.name()).isPresent()){
            throw new BusinessRuleException("Categoria não existe");
        }

        Category category = new Category();
        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());
        category.setCategoryGender(categoryRequestDTO.categoryGender());

        return categoryRepository.save(category);
    }
}

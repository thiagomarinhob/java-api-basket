package com.basket.api.web;

import com.basket.api.model.useCase.category.CategoryRequestDTO;
import com.basket.api.model.entity.Category;
import com.basket.api.model.useCase.category.CreateCategoryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Tag(name = "6. Categorias", description = "Endpoints para gerenciamento de categorias de time")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova categoria", description = "Cria uma nova categoria (ex: 'Sub-18 Masculino'). Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Categoria com este nome já existe")
    })
    private ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        Category category = createCategoryUseCase.execute(categoryRequestDTO);
        return ResponseEntity.ok(category);
    }
}

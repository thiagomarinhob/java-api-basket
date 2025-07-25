package com.basket.api.modules.user.controller;

import com.basket.api.modules.user.entity.UserEntity;
import com.basket.api.modules.user.useCases.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity) {
        var result = this.createUserUseCase.execute(userEntity);
        return ResponseEntity.ok().body(result);
    }
}

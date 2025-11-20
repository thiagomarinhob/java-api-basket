package com.basket.api.model.useCase.user;

public record CreateUserRequest(String name, String email, String password) {
}

package com.basket.api.domain.useCase.user;

public record CreateUserRequest(String name, String email, String password) {
}

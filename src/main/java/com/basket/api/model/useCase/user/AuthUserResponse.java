package com.basket.api.model.useCase.user;

public record AuthUserResponse(String access_token, Long expires_in) {
}

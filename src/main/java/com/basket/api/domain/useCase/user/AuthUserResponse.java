package com.basket.api.domain.useCase.user;

public record AuthUserResponse(String access_token, Long expires_in) {
}

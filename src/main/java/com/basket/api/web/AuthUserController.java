package com.basket.api.web;


import com.basket.api.domain.useCase.user.AuthUserRequest;
import com.basket.api.domain.useCase.user.DefaultAuthUserUseCase;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AuthUserController implements AuthUserAPI {


    private final DefaultAuthUserUseCase defaultAuthUserUseCase;


    @Override
    public Object signIn(@Valid @RequestBody AuthUserRequest authUserRequest) throws AuthenticationException {
        return defaultAuthUserUseCase.execute(authUserRequest);
    }
}

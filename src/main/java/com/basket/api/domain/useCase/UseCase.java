package com.basket.api.domain.useCase;

import javax.naming.AuthenticationException;

public interface UseCase<IN, OUT> {

    OUT execute(IN in) throws AuthenticationException;

}

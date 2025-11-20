package com.basket.api.model.useCase;

import javax.naming.AuthenticationException;

public interface UseCase<IN, OUT> {

    OUT execute(IN in) throws AuthenticationException;

}

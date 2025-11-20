package com.basket.api.domain.useCase.user;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.domain.entity.User;
import com.basket.api.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultCreateUserUseCase implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(CreateUserRequest in) {
        userRepository
                .findByEmail(in.email())
                .ifPresent((user) -> {
                    throw new BusinessRuleException("Email already exists: " + in.email());
                });

        var password = this.passwordEncoder.encode(in.password());

        final User user = new User();
        user.setName(in.name());
        user.setEmail(in.email());
        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

}

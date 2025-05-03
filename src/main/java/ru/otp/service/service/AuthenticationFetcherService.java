package ru.otp.service.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.entity.UserEntity;
import ru.otp.service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationFetcherService {

    private final UserRepository userRepository;

    public UserEntity getUser() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name)
                .orElseThrow();
    }

}

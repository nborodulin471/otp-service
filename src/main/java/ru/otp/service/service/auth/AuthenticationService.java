package ru.otp.service.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.mappers.UserMapper;
import ru.otp.service.repository.UserRepository;
import ru.otp.service.service.UserService;

/**
 * Сервис авторизации.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserDto registerNewUser(UserDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        var res = userRepository.save(userMapper.toEntity(user));

        return userMapper.toDto(res);
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        return jwtTokenProvider.generateToken(user);
    }
}

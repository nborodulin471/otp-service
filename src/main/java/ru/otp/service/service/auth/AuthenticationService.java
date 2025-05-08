package ru.otp.service.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.exception.OtpAuthException;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.enums.Role;
import ru.otp.service.model.mappers.UserMapper;
import ru.otp.service.repository.UserRepository;
import ru.otp.service.service.UserService;

/**
 * Сервис авторизации.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    public void registerNewUser(UserDto user) {
        log.info("Registering new user with username: {}", user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("User with username: {} already exists", user.getUsername());
            throw new OtpAuthException("Пользователь с таким именем уже существует");
        }

        var userEntity = userMapper.toEntity(user);

        if (userEntity.getRole() == Role.ROLE_ADMIN && userRepository.findByRole(userEntity.getRole()).size() > 1) {
            throw new OtpAuthException("Администратор может быть только один");
        }

        userEntity.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(userEntity);

        log.info("User registered successfully with username: {}", user.getUsername());
    }

    public String login(String username, String password) {
        log.info("User attempting to log in with username: {}", username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        String token = jwtTokenProvider.generateToken(user);
        log.info("User logged in successfully with username: {}", username);

        return token;
    }
}

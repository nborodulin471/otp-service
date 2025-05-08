package ru.otp.service.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.entity.User;
import ru.otp.service.model.enums.Role;
import ru.otp.service.model.mappers.UserMapper;
import ru.otp.service.repository.UserRepository;


/**
 * Сервис для работы с пользователями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto get(long id) {
        log.info("Fetching user with ID: {}", id);
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(() -> {
                    log.warn("User with ID: {} not found", id);
                    return new RuntimeException("User not found");
                })
        );
    }

    public UserDto edit(long id, UserDto userDto) {
        log.info("Editing user with ID: {}", id);
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with ID: {} not found for editing", id);
            return new RuntimeException("User not found");
        });

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));

        log.info("User with ID: {} updated successfully", id);
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(long id) {
        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
        log.info("User with ID: {} deleted successfully", id);
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching current user: {}", username);
        return getByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User with username: {} not found", username);
                    return new UsernameNotFoundException("Пользователь не найден");
                });
    }
}

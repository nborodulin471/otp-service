package ru.otp.service.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.entity.User;
import ru.otp.service.model.enums.Role;
import ru.otp.service.model.mappers.UserMapper;
import ru.otp.service.repository.OtpRepository;
import ru.otp.service.repository.UserRepository;

import java.util.List;


/**
 * Сервис для работы с пользователями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        log.info("Fetching all user");
        return userRepository.findByRole(Role.ROLE_USER).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto get(long id) {
        log.info("Fetching user with ID: {}", id);
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(() -> {
                    log.warn("User with ID: {} not found", id);
                    return new EntityNotFoundException("User not found");
                })
        );
    }

    public void delete(long id) {
        log.info("Deleting user with ID: {}", id);
        var allByUserId = otpRepository.findAllByUser(getCurrentUser());
        otpRepository.deleteAll(allByUserId);
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

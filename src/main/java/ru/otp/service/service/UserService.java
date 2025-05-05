package ru.otp.service.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.entity.User;
import ru.otp.service.model.enums.Role;
import ru.otp.service.model.mappers.UserMapper;
import ru.otp.service.repository.UserRepository;


/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto get(long id) {
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow()
        );
    }

    public UserDto edit(long id, UserDto userDto) {
        var user = userRepository.findById(id).orElseThrow();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));

        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

}

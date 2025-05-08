package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import ru.otp.service.model.dto.UserDto;
import ru.otp.service.model.entity.User;
import ru.otp.service.model.enums.Role;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getUsername(),
                null, // уберем, чтобы не светить пароль
                user.getRole().name()
        );
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.valueOf(userDto.getRole()));

        return user;
    }
}

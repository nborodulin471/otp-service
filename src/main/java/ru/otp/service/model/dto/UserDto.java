package ru.otp.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Пользователь.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class UserDto {
    private String username;
    private String password;
    private String role;
}

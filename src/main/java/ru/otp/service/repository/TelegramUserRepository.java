package ru.otp.service.repository;

import org.springframework.data.repository.CrudRepository;

import ru.otp.service.model.entity.TelegramUser;

import java.util.Optional;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByUsername(String username);
}

package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}

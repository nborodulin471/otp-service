package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.User;
import ru.otp.service.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRole(Role role);
}

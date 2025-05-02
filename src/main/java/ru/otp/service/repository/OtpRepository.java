package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
}

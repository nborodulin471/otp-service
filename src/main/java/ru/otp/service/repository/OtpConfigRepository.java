package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.OperationEntity;
import ru.otp.service.model.entity.OtpConfigEntity;

public interface OtpConfigRepository extends JpaRepository<OtpConfigEntity, Long> {
}

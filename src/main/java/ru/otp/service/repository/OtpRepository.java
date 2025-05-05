package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.OtpEntity;
import ru.otp.service.model.enums.OtpStatus;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    Optional<OtpEntity> findByCode(String code);

    List<OtpEntity> findByOtpStatus(OtpStatus status);
}

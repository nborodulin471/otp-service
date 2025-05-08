package ru.otp.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otp.service.model.entity.OperationEntity;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    List<OperationEntity> findAllByInitiatorOrderByCreatedAtDesc(String initiator);

    Optional<OperationEntity> findByIdAndInitiator(Long id, String initiator);
}

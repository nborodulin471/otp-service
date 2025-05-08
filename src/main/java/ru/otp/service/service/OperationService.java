package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.dto.OperationDto;
import ru.otp.service.model.entity.OperationEntity;
import ru.otp.service.repository.OperationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    public OperationEntity getBy(long operationId) {
        log.info("Fetching operation with ID: {}", operationId);
        return operationRepository
                .findById(operationId)
                .orElseThrow(() -> {
                    log.warn("Operation with ID: {} not found", operationId);
                    return new RuntimeException("Operation not found");
                });
    }

    public OperationDto create(OperationDto operationDto) {
        log.info("Creating new operation with details: {}", operationDto);
        // Здесь должна быть логика создания операции
        // Например, сохранение операции в базе данных
        // OperationEntity operationEntity = new OperationEntity();
        // operationEntity.setDetails(operationDto.getDetails());
        // operationRepository.save(operationEntity);

        // Возвращаем созданный объект DTO
        return new OperationDto(); // Замените на фактическую логику создания
    }
}

package ru.otp.service.model.dto;

/**
 * Дто операции.
 * <p>
 * {@link ru.otp.service.model.entity.OperationEntity}
 */
public record OperationDto(Long sum, String destination, String operationType, String status,
                           String initiator, String description) {
}

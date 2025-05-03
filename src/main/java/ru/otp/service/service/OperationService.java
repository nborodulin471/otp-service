package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OperationDto;
import ru.otp.service.model.entity.OperationEntity;
import ru.otp.service.repository.OperationRepository;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    public OperationEntity getBy(long operationId) {
        return operationRepository
                .findById(operationId)
                .orElseThrow();
    }

    public OperationDto create(OperationDto operationDto) {
        return new OperationDto();
    }
}

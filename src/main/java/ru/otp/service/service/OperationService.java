package ru.otp.service.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OperationDto;
import ru.otp.service.model.entity.OperationEntity;
import ru.otp.service.repository.OperationRepository;
import ru.otp.service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    public OperationEntity createOperation(OperationDto request, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var operation = new OperationEntity();
        operation.setSum(request.sum());
        operation.setDestination(request.destination());
        operation.setOperationType(request.operationType());
        operation.setStatus("PENDING");
        operation.setInitiator(username);
        operation.setDescription(request.description());
        operation.setUser(user);
        operation.setCreatedAt(LocalDateTime.now());

        return operationRepository.save(operation);
    }

    public OperationEntity edit(Long operationId, OperationDto request, String username) {
        var operation = operationRepository.findByIdAndInitiator(operationId, username)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found"));

        operation.setStatus(request.status());
        operation.setDescription(request.description());

        return operationRepository.save(operation);
    }

    public List<OperationEntity> getUserOperations(String username) {
        return operationRepository.findAllByInitiatorOrderByCreatedAtDesc(username);
    }

    public OperationEntity getOperationDetails(Long id, String username) {
        return operationRepository.findByIdAndInitiator(id, username)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found"));
    }
}

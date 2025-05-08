package ru.otp.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OperationDto;
import ru.otp.service.model.mappers.OperationMapper;
import ru.otp.service.service.OperationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operation")
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @PostMapping
    public ResponseEntity<OperationDto> createOperation(@RequestBody OperationDto request,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        var operation = operationService.createOperation(request, userDetails.getUsername());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationMapper.mapToDto(operation));
    }

    @PostMapping("/{id}")
    public ResponseEntity<OperationDto> edit(@PathVariable Long id, @RequestBody OperationDto request,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        var operation = operationService.edit(id, request, userDetails.getUsername());

        return ResponseEntity.ok(operationMapper.mapToDto(operation));
    }

    @GetMapping
    public ResponseEntity<List<OperationDto>> getUserOperations(
            @AuthenticationPrincipal UserDetails userDetails) {

        var operations = operationService.getUserOperations(userDetails.getUsername());

        return ResponseEntity.ok(
                operations.stream()
                        .map(operationMapper::mapToDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDto> getOperationDetails(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        var operation = operationService.getOperationDetails(id, userDetails.getUsername());

        return ResponseEntity.ok(operationMapper.mapToDto(operation));
    }

}

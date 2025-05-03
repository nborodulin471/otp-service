package ru.otp.service.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operation")
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper mapper;

    @GetMapping("/{id}")
    public OperationDto getOperation(@PathVariable long id) {
        return mapper.mapToDto(operationService.getBy(id));
    }

    @PostMapping
    public OperationDto createOperation(@RequestBody OperationDto operationDto) {
        return operationService.create(operationDto);
    }

}

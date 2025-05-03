package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import ru.otp.service.model.dto.OperationDto;
import ru.otp.service.model.entity.OperationEntity;

@Component
public class OperationMapper {
    public OperationDto mapToDto(OperationEntity entity) {
        return new OperationDto();
    }
}

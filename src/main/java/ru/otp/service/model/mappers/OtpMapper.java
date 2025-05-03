package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import ru.otp.service.model.dto.OtpDto;
import ru.otp.service.model.entity.OtpEntity;

@Component
public class OtpMapper {

    public OtpDto mapToDto(OtpEntity entity) {
        return new OtpDto(
                entity.getCode()
        );
    }
}

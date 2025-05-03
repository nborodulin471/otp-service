package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.entity.OtpConfigEntity;

@Component
public class OtpConfigMapper {

    public OtpConfigDto mapToDto(OtpConfigEntity config) {
        return new OtpConfigDto(config.getTtl(), config.getLength());
    }
}

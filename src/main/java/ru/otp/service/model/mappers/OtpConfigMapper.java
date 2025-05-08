package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.entity.DeliveryType;
import ru.otp.service.model.entity.OtpConfigEntity;
import ru.otp.service.service.UserService;

@Component
@RequiredArgsConstructor
public class OtpConfigMapper {

    private final UserService userService;

    public OtpConfigDto mapToDto(OtpConfigEntity config) {
        return new OtpConfigDto(config.getTtl(), config.getLength(), config.getDeliveryType().name());
    }

    public OtpConfigEntity mapToEntity(OtpConfigDto config) {
        return new OtpConfigEntity(0l, userService.getCurrentUser(), config.ttl(), config.length(),
                DeliveryType.valueOf(config.deliveryType()));
    }
}

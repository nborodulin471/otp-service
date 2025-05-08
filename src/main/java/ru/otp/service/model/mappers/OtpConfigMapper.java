package ru.otp.service.model.mappers;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.entity.DeliveryType;
import ru.otp.service.model.entity.OtpConfigEntity;
import ru.otp.service.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class OtpConfigMapper {

    private final UserRepository userRepository;

    public OtpConfigDto mapToDto(OtpConfigEntity config) {
        return new OtpConfigDto(config.getTtl(), config.getLength(), config.getDeliveryType().name(), config.getUser().getId());
    }

    public OtpConfigEntity mapToEntity(OtpConfigDto config) {
        var user = userRepository.findById(config.userId()).orElseThrow();
        return new OtpConfigEntity(0L, user, config.ttl(), config.length(),
                DeliveryType.valueOf(config.deliveryType()));
    }
}

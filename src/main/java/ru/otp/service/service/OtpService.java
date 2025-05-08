package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.dto.OtpDto;
import ru.otp.service.model.entity.OtpEntity;
import ru.otp.service.model.enums.OtpStatus;
import ru.otp.service.model.mappers.OtpMapper;
import ru.otp.service.repository.OperationRepository;
import ru.otp.service.repository.OtpRepository;
import ru.otp.service.service.distribution.DeliveryManager;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {

    private final OperationRepository operationRepository;
    private final OtpConfigService otpConfigService;
    private final UserService userService;
    private final OtpRepository otpRepository;
    private final OtpMapper otpMapper;
    private final DeliveryManager deliveryManager;

    private final Random random = new Random();

    public OtpDto generate(long operationId) {
        var config = otpConfigService.getCurrentConfig();
        var code = generateRandomCode(config.getLength());

        var operation = operationRepository.findById(operationId).orElseThrow();

        var otpCode = new OtpEntity();
        otpCode.setOperation(operation);
        otpCode.setCode(code);
        otpCode.setOtpStatus(OtpStatus.ACTIVE);
        otpCode.setExpiresAt(LocalDateTime.now().plusSeconds(config.getTtl()));
        otpCode.setUser(userService.getCurrentUser());

        log.info("Generated OTP code: {} for operation ID: {}", code, operationId);

        deliveryManager.getDeliveryService(config.getDeliveryType())
                .send("Your verification code " + code, operation.getDestination());

        return otpMapper.mapToDto(otpRepository.save(otpCode));
    }

    public OtpStatus validate(long operationId, String code) {
        log.info("Validating OTP code: {} for operation ID: {}", code, operationId);
        var otpCodeOpt = otpRepository.findByCode(code);

        if (otpCodeOpt.isEmpty()) {
            log.warn("OTP code not found: {}", code);
            return OtpStatus.NOT_FOUND;
        }

        var otpCode = otpCodeOpt.get();
        if (otpCode.getOperation().getId() != operationId) {
            log.warn("Operation ID mismatch for OTP code: {}. Expected: {}, Found: {}", code, operationId, otpCode.getOperation().getId());
            return OtpStatus.OPERATION_NOT_FOUND;
        }

        if (LocalDateTime.now().isAfter(otpCode.getExpiresAt())) {
            otpCode.setOtpStatus(OtpStatus.EXPIRED);
            otpRepository.save(otpCode);
            log.info("OTP code expired: {}", code);
            return OtpStatus.EXPIRED;
        }

        return otpCode.getOtpStatus();
    }

    private String generateRandomCode(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

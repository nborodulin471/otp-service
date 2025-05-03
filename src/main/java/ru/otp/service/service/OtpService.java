package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.OtpStatus;
import ru.otp.service.model.dto.OtpDto;
import ru.otp.service.model.entity.OtpEntity;
import ru.otp.service.model.mappers.OtpMapper;
import ru.otp.service.repository.OtpRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OperationService operationService;
    private final OtpConfigService otpConfigService;
    private final OtpRepository otpRepository;
    private final OtpMapper otpMapper;

    private final Random random = new Random();

    public OtpDto generate(long operationId) {
        var config = otpConfigService.getCurrentConfig();
        var code = generateRandomCode(config.length());

        var otpCode = new OtpEntity();
        otpCode.setOperation(operationService.getBy(operationId));
        otpCode.setCode(code);
        otpCode.setOtpStatus(OtpStatus.ACTIVE);
        otpCode.setExpiresAt(LocalDateTime.now().plusSeconds(config.ttl()));

        return otpMapper.mapToDto(otpRepository.save(otpCode));
    }

    public OtpStatus validate(long operationId, String code) {
        Optional<OtpEntity> otpCodeOpt = otpRepository.findByCode(code);

        if (otpCodeOpt.isEmpty()) {
            return OtpStatus.NOT_FOUND;
        }

        var otpCode = otpCodeOpt.get();
        if(otpCode.getOperation().getId() != operationId){
            return OtpStatus.OPERATION_NOT_FOUND;
        }

        if (LocalDateTime.now().isAfter(otpCode.getExpiresAt())) {
            otpCode.setOtpStatus(OtpStatus.EXPIRED);
            otpRepository.save(otpCode);

            return OtpStatus.EXPIRED;
        }

        if (otpCode.getOtpStatus() == OtpStatus.ACTIVE){
            return OtpStatus.INACTIVE;
        }

        otpCode.setOtpStatus(OtpStatus.USED);
        otpRepository.save(otpCode);

        return OtpStatus.USED;
    }

    private String generateRandomCode(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

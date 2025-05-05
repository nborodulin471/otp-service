package ru.otp.service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.enums.OtpStatus;
import ru.otp.service.repository.OtpRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpirationService {

    private final OtpRepository otpRepository;

    @Scheduled(fixedRateString = "${otp.ttl}")
    public void expired() {
        var now = LocalDateTime.now();
        var entities = otpRepository.findByOtpStatus(OtpStatus.ACTIVE);

        entities.stream()
                .filter(otp -> now.isAfter(otp.getExpiresAt()))
                .forEach(otp -> {
                    otp.setOtpStatus(OtpStatus.EXPIRED);
                    otpRepository.save(otp);
                });
    }
}

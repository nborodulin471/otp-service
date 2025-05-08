package ru.otp.service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.enums.OtpStatus;
import ru.otp.service.repository.OtpRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpirationService {

    private final OtpRepository otpRepository;

    @Scheduled(fixedRateString = "${otp.ttl}")
    public void expired() {
        var now = LocalDateTime.now();
        log.info("Checking for expired OTPs at {}", now);
        var entities = otpRepository.findByOtpStatus(OtpStatus.ACTIVE);
        log.info("Found {} active OTP(s)", entities.size());

        entities.stream()
                .filter(otp -> now.isAfter(otp.getExpiresAt()))
                .forEach(otp -> {
                    otp.setOtpStatus(OtpStatus.EXPIRED);
                    otpRepository.save(otp);
                    log.info("Expired OTP with ID: {}", otp.getId());
                });
        log.info("Expired OTP processing completed.");
    }
}

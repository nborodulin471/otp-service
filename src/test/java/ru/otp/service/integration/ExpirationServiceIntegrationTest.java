package ru.otp.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otp.service.model.entity.OtpEntity;
import ru.otp.service.model.enums.OtpStatus;
import ru.otp.service.repository.OtpRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "otp.ttl=1000" // Частота проверки - 1 секунда
})
class ExpirationServiceIntegrationTest {

    @Autowired
    private OtpRepository otpRepository;

    @BeforeEach
    void setUp() {
        otpRepository.deleteAll();
    }

    @Test
    void shouldExpireRecordsAfterTtl() throws InterruptedException {
        var now = LocalDateTime.now();
        var past = now.minusMinutes(5);
        var future = now.plusMinutes(5);

        // Создаем активные OTP - один просроченный, один нет
        var expiredOtp = new OtpEntity();
        expiredOtp.setCode("111111");
        expiredOtp.setOtpStatus(OtpStatus.ACTIVE);
        expiredOtp.setExpiresAt(past);
        otpRepository.save(expiredOtp);

        var activeOtp = new OtpEntity();
        activeOtp.setCode("222222");
        activeOtp.setOtpStatus(OtpStatus.ACTIVE);
        activeOtp.setExpiresAt(future);
        otpRepository.save(activeOtp);

        // ждем срабатывания шедулера (1 секунда)
        Thread.sleep(1500); // Даем небольшой запас времени

        // Assert - проверяем, что только просроченный OTP изменил статус
        var allOtps = otpRepository.findAll();

        var updatedExpiredOtp = allOtps.stream()
                .filter(o -> o.getCode().equals("111111"))
                .findFirst()
                .orElseThrow();
        assertEquals(OtpStatus.EXPIRED, updatedExpiredOtp.getOtpStatus());

        var notUpdatedOtp = allOtps.stream()
                .filter(o -> o.getCode().equals("222222"))
                .findFirst()
                .orElseThrow();
        assertEquals(OtpStatus.ACTIVE, notUpdatedOtp.getOtpStatus());
    }
}

package ru.otp.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otp.service.service.distribution.SmsService;

@SpringBootTest
class SmsServiceTest {

    @Autowired
    private SmsService smsService;

    @Test
    void testSendSms() {
        try {
            smsService.send("123456", "79161234567", "Код: {code}");
            Assertions.assertTrue(true, "SMS отправлено успешно");
        } catch (Exception e) {
            Assertions.fail("Ошибка отправки SMS: " + e.getMessage());
        }
    }

}

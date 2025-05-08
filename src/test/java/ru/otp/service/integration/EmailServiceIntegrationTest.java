package ru.otp.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import ru.otp.service.service.distribution.EmailService;

@SpringBootTest
class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        // Given
        String destination = "nborodulin471@gmail.com";
        String template = "Your verification code 123456";

        // When
        emailService.send(template, destination);

        // Then
        // Ждем получения письма (макс 5 секунд)
    }

}

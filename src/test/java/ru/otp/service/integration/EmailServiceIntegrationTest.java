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
    void testSendEmail() throws Exception {
        // Given
        String code = "123456";
        String destination = "nborodulin471@gmail.com";
        String template = "Your verification code is {code}. Please enter it to verify your account.";

        // When
        emailService.send(code, destination, template);

        // Then
        // Ждем получения письма (макс 5 секунд)

    }

}

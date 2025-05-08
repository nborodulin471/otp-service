package ru.otp.service.service.distribution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements DeliveryService {

    private final JavaMailSender mailSender;

    @Override
    public void send(String code, String destination, String template) {
        String messageContent = template.replace("{code}", code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destination);
        message.setSubject("Your Verification Code");
        message.setText(messageContent);

        log.info("Sending email to: {}", destination);
        log.debug("Email content: {}", messageContent);

        try {
            mailSender.send(message);
            log.info("Email sent successfully to: {}", destination);
        } catch (Exception e) {
            log.error("Failed to send email to: {}. Error: {}", destination, e.getMessage());
        }
    }
}


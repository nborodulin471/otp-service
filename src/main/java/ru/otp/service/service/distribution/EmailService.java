package ru.otp.service.service.distribution;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements DeliveryService {

    private final JavaMailSender mailSender;

    @Override
    public void send(String code, String destination, String template) {
        String messageContent = template.replace("{code}", code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destination);
        message.setSubject("Your Verification Code ");
        message.setText(messageContent);

        mailSender.send(message);
    }
}

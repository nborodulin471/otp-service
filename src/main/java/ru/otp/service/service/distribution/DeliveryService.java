package ru.otp.service.service.distribution;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface DeliveryService {
    void send(String code, String destination, String template) throws TelegramApiException;
}

package ru.otp.service.service.distribution;

import org.springframework.stereotype.Service;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otp.service.config.TelegramConfig;
import ru.otp.service.repository.TelegramUserRepository;

@Service
public class TelegramService extends DefaultAbsSender implements DeliveryService {

    private final TelegramUserRepository telegramUserRepository;

    protected TelegramService(DefaultBotOptions options, TelegramConfig telegramConfig, TelegramUserRepository telegramUserRepository) {
        super(options, telegramConfig.getApiKey());
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void send(String code, String destination, String template) throws TelegramApiException {
        var telegramUser = telegramUserRepository.findByUsername(destination)
                .orElseThrow(() -> new RuntimeException("Пользователь должен сначала запустить чат"));

        var chatId = telegramUser.getChatId();
        var text = template.replace("{code}", code);

        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableHtml(true);


        execute(message);

    }
}

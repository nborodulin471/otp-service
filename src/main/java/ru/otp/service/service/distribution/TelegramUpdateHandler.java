package ru.otp.service.service.distribution;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.otp.service.config.TelegramConfig;
import ru.otp.service.model.entity.TelegramUser;
import ru.otp.service.repository.TelegramUserRepository;

@Component
public class TelegramUpdateHandler extends TelegramLongPollingBot {

    private final TelegramUserRepository telegramUserRepository;

    public TelegramUpdateHandler(TelegramConfig telegramConfig, TelegramUserRepository telegramUserRepository) {
        super(telegramConfig.getApiKey());
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            User user = update.getMessage().getFrom();
            TelegramUser telegramUser = new TelegramUser(user.getId(), user.getUserName(), chatId);
            telegramUserRepository.save(telegramUser);
        }
    }

    @Override
    public String getBotUsername() {
        return "Otp_chat_bot";
    }
}

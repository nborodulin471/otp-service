package ru.otp.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otp.service.model.entity.TelegramUser;
import ru.otp.service.repository.TelegramUserRepository;
import ru.otp.service.service.distribution.TelegramService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тест для проверки работы бота.
 *
 * @implNote данный тест будет отключен т.к написан исключительно в целях разработки для проверки функционала.
 * Вам надо получить TEST_CHAT_ID бота, а также подставить его апи кей и проверить что в бот приходят сообщения
 */
@Disabled
@SpringBootTest
class TelegramServiceIntegrationTest {
    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Autowired
    private TelegramService telegramService;

    private static final String TEST_USERNAME = "testuser";
    private static final long TEST_CHAT_ID = 978297678;
    private static final String TEST_MESSAGE = "Your verification code 123456";

    @BeforeEach
    void setUp() {
        telegramUserRepository.deleteAll();
    }

    @Test
    void send_ShouldSendMessageToUser_WhenUserExists() {
        var testUser = new TelegramUser();
        testUser.setUsername(TEST_USERNAME);
        testUser.setChatId(TEST_CHAT_ID);
        telegramUserRepository.save(testUser);

        assertDoesNotThrow(() -> {
            telegramService.send(TEST_MESSAGE, TEST_USERNAME);
        });

        // проверяем, что сообщение было отправлено
    }

    @Test
    void send_ShouldThrowException_WhenUserNotFound() {
        var exception = assertThrows(RuntimeException.class, () -> {
            telegramService.send(TEST_MESSAGE, "nonexistentuser");
        });

        assertEquals("Пользователь должен сначала запустить чат", exception.getMessage());
    }

    @Test
    void send_ShouldActuallySendMessage_WhenUsingTestBot() throws TelegramApiException {
        var testUser = new TelegramUser();
        testUser.setUsername(TEST_USERNAME);
        testUser.setChatId(TEST_CHAT_ID);
        telegramUserRepository.save(testUser);

        telegramService.send(TEST_MESSAGE, TEST_USERNAME);
    }
}

package ru.otp.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.otp.service.service.distribution.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "otp.log.file.path=test/test-sms.log",
        "otp.log.file.append=true",
        "otp.log.file.timestamp=true"
})
class FileTest {
    @Autowired
    private FileService fileService;

    private final Path logFilePath = Paths.get("test/test-sms.log");

    @AfterEach
    void clear() throws IOException {
        if (Files.exists(logFilePath)) {
            Files.delete(logFilePath);
        }
    }

    @Test
    void shouldWriteCodeToFile() throws IOException {
        // Given
        var code = "123456";
        var destination = "79161234567";
        var template = "Ваш код: {code}";

        // When
        fileService.send(code, destination, template);

        // Then
        assertTrue(Files.exists(logFilePath), "Файл должен быть создан");

        var lines = Files.readAllLines(logFilePath);
        assertEquals(1, lines.size(), "Должна быть одна запись в файле");
        assertTrue(lines.get(0).contains(code), "Запись должна содержать код");
        assertTrue(lines.get(0).contains(destination), "Запись должна содержать номер");
    }

    @Test
    void shouldAppendMultipleCodes() throws IOException {
        // Given
        String[] codes = {"111111", "222222", "333333"};

        // When
        for (String code : codes) {
            fileService.send(code, "79160000000", "Code: {code}");
        }

        // Then
        var lines = Files.readAllLines(logFilePath);
        assertEquals(codes.length, lines.size(), "Все коды должны быть записаны");
        for (int i = 0; i < codes.length; i++) {
            assertTrue(lines.get(i).contains(codes[i]),
                    "Строка " + (i + 1) + " должна содержать код " + codes[i]);
        }
    }
}

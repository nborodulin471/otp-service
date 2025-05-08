package ru.otp.service.service.distribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otp.service.exception.LogFileWriteException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileService implements DeliveryService {

    private final Path logFilePath;
    private final boolean appendMode;
    private final boolean includeTimestamp;

    @Autowired
    public FileService(
            @Value("${otp.log.file.path}") String filePath,
            @Value("${otp.log.file.append:true}") boolean appendMode,
            @Value("${otp.log.file.timestamp:true}") boolean includeTimestamp
    ) {
        this.logFilePath = Paths.get(filePath);
        this.appendMode = appendMode;
        this.includeTimestamp = includeTimestamp;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        try {
            if (!Files.exists(logFilePath)) {
                Files.createDirectories(logFilePath.getParent());
                Files.createFile(logFilePath);
            }
        } catch (IOException e) {
            throw new LogFileWriteException("Failed to create log file", e);
        }
    }

    @Override
    public void send(String code, String destination, String template) {
        String message = template.replace("{code}", code);
        String logEntry = formatLogEntry(code, destination, message);

        try (BufferedWriter writer = Files.newBufferedWriter(logFilePath,
                StandardCharsets.UTF_8,
                appendMode ? StandardOpenOption.APPEND : StandardOpenOption.WRITE,
                StandardOpenOption.CREATE)) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            throw new LogFileWriteException("Failed to write to log file", e);
        }
    }

    private String formatLogEntry(String code, String destination, String message) {
        StringBuilder sb = new StringBuilder();

        if (includeTimestamp) {
            sb.append("[")
                    .append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("] ");
        }

        sb.append("Destination: ").append(destination)
                .append(" | Code: ").append(code)
                .append(" | Message: '").append(message).append("'");

        return sb.toString();
    }
}
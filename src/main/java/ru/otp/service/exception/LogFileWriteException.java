package ru.otp.service.exception;

public class LogFileWriteException extends RuntimeException {
    
    public LogFileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

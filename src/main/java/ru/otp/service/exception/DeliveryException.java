package ru.otp.service.exception;

public class DeliveryException extends RuntimeException {
    public DeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}

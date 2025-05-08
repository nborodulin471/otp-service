package ru.otp.service.exception;

public class OtpAuthException extends RuntimeException {
    public OtpAuthException(String message) {
        super(message);
    }
}

package ru.otp.service.model.dto;

public record OtpConfigDto(long ttl, int length, String deliveryType, long userId) {
}

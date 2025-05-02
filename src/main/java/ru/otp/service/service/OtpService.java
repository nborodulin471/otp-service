package ru.otp.service.service;

import org.springframework.stereotype.Service;

import ru.otp.service.model.dto.OtpDto;

@Service
public class OtpService {
    public OtpDto generate(long operationId) {
        return null;
    }

    public boolean validate(long operationId, String code) {
        return false;
    }
}

package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.mappers.OtpConfigMapper;
import ru.otp.service.repository.OtpConfigRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpConfigService {

    private final OtpConfigRepository otpConfigRepository;
    private final OtpConfigMapper otpConfigMapper;
    private final UserService userService;

    public OtpConfigDto edit(long id, OtpConfigDto otpCodeConfigDto) {
        log.info("Editing OTP config with ID: {}", id);
        var config = otpConfigRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("OTP config with ID: {} not found", id);
                    return new RuntimeException("OTP config not found");
                });

        config.setTtl(otpCodeConfigDto.ttl());
        config.setLength(otpCodeConfigDto.length());

        var updatedConfig = otpConfigRepository.save(config);
        log.info("OTP config with ID: {} updated successfully", id);
        return otpConfigMapper.mapToDto(updatedConfig);
    }

    public OtpConfigDto getCurrentConfig() {
        var currentUser = userService.getCurrentUser();
        log.info("Fetching current OTP config for user: {}", currentUser.getUsername());
        var config = otpConfigRepository.findByUser(currentUser)
                .orElseThrow(() -> {
                    log.warn("No OTP config found for user: {}", currentUser.getUsername());
                    return new RuntimeException("OTP config not found for current user");
                });

        return otpConfigMapper.mapToDto(config);
    }
}

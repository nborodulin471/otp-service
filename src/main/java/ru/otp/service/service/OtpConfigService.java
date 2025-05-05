package ru.otp.service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.mappers.OtpConfigMapper;
import ru.otp.service.repository.OtpConfigRepository;

@Service
@RequiredArgsConstructor
public class OtpConfigService {

    private final OtpConfigRepository otpConfigRepository;
    private final OtpConfigMapper otpConfigMapper;
    private final UserService userService;

    public OtpConfigDto edit(long id, OtpConfigDto otpCodeConfigDto) {
        var config = otpConfigRepository.findById(id)
                .orElseThrow();

        config.setTtl(otpCodeConfigDto.ttl());
        config.setLength(otpCodeConfigDto.length());

        return otpConfigMapper.mapToDto(otpConfigRepository.save(config));
    }

    public OtpConfigDto getCurrentConfig() {
        var config = otpConfigRepository.findByUser(userService.getCurrentUser()).orElseThrow();

        return otpConfigMapper.mapToDto(config);
    }
}

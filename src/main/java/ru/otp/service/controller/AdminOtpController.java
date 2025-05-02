package ru.otp.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.service.OtpConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/otp/config")
public class AdminOtpController {

    private final OtpConfigService otpConfigService;

    @GetMapping
    public ResponseEntity<OtpConfigDto> getConfig() {
        return ResponseEntity.ok(
                otpConfigService.getCurrentConfig()
        );

    }

    @PostMapping
    public ResponseEntity<OtpConfigDto> changeConfig(@RequestBody OtpConfigDto otpCodeConfigDTO) {
        return ResponseEntity.ok(
                otpConfigService.edit(otpCodeConfigDTO)
        );
    }

}

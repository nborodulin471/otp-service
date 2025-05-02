package ru.otp.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpDto;
import ru.otp.service.service.OtpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operation/{operationId}/otp")
public class UserOtpController {

    private final OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<OtpDto> generate(@PathVariable long operationId) {
        return ResponseEntity.ok(
                otpService.generate(operationId)
        );
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@PathVariable long operationId, @RequestParam String code) {
        if (otpService.validate(operationId, code)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}

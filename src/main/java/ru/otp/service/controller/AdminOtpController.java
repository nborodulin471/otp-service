package ru.otp.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.dto.OtpConfigDto;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.service.OtpConfigService;
import ru.otp.service.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminOtpController {

    private final OtpConfigService otpConfigService;
    private final UserService userService;

    @PostMapping("/config")
    public ResponseEntity<OtpConfigDto> createOtpConfig(@RequestBody OtpConfigDto otpConfigDto) {
        return ResponseEntity.ok(
                otpConfigService.create(otpConfigDto)
        );
    }

    @PostMapping("/config/{id}")
    public ResponseEntity<OtpConfigDto> changeConfig(@PathVariable long id, @RequestBody OtpConfigDto otpCodeConfigDTO) {
        return ResponseEntity.ok(
                otpConfigService.edit(id, otpCodeConfigDTO)
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }

}

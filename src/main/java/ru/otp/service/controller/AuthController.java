package ru.otp.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otp.service.model.api.AuthenticationRequest;
import ru.otp.service.model.dto.UserDto;
import ru.otp.service.service.auth.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", authenticationService.login(request.username(), request.password()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> showRegistrationForm(@RequestBody UserDto user) {
        authenticationService.registerNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Аккаунт успешно зарегистрирован.");
    }

}

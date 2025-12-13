package ru.rsreu.lab.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.dto.request.JwtRequestDTO;
import ru.rsreu.lab.model.dto.response.JwtResponseDTO;
import ru.rsreu.lab.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "Аутентификация и регистрация пользователей")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Вход в систему")
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> signIn(@RequestBody JwtRequestDTO authRequest) {
        return ResponseEntity.ok(authService.signIn(authRequest));
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> signUp(@RequestBody JwtRequestDTO register) throws BadRequestException {
        return ResponseEntity.ok(authService.signUp(register));
    }
}
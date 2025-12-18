package ru.rsreu.lab.model.dto.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestClientLoginForm {
    @NotBlank(message = "Логин обязателен")
    private String login;

    @NotBlank(message = "Пароль обязателен")
    private String password;
}

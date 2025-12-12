package ru.rsreu.lab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequestDTO {
    @Size(min = 3, max = 20, message = "Логин должен быть от 3 до 20 символов")
    private String login;

    @Size(min = 3, max = 20, message = "Пароль должен быть от 3 до 20 символов")
    private String password;
}

package ru.rsreu.lab.model.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestClientFormatForm {

    @NotBlank(message = "Введите текст для форматирования")
    @Size(min = 3, max = 5000, message = "Текст должен содержать от 3 до 5000 символов")
    private String originalText;

    @NotNull(message = "Выберите формат")
    private Long formatId;
}

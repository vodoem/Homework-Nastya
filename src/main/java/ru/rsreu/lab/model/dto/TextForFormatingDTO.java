package ru.rsreu.lab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TextForFormatingDTO {
    @Size(min = 3, max = 5000, message = "Текст должен быть от 3 до 5000 символов")
    private String text;

    @NotBlank(message = "Не выбран формат")
    private String format;

}
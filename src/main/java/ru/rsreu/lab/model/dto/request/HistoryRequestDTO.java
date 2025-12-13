package ru.rsreu.lab.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRequestDTO {
    @NotBlank(message = "Текст не может быть пустым")
    @Size(min = 3, max = 5000, message = "Текст должен быть от 3 до 5000 символов")
    private String originalText;

    @NotNull(message = "ID формата обязателен")
    private Long formatId;

}
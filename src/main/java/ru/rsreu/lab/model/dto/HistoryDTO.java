package ru.rsreu.lab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.Format;

@Data
@AllArgsConstructor
@Builder
public class HistoryDTO {

    @Size(min = 3, max = 5000, message = "Текст должен быть от 3 до 5000 символов")
    private String originalText;

    private String formattedText;

    private Long formatId;
}

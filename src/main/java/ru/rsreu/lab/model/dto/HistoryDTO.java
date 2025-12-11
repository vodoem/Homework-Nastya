package ru.rsreu.lab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.Format;

@Data
@AllArgsConstructor
@Builder
public class HistoryDTO {
    private String originalText;
    private String formattedText;
    private Long formatId;
}

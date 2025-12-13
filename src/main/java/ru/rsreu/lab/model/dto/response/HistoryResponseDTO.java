package ru.rsreu.lab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponseDTO {
    private Long id;
    private String originalText;
    private String formattedText;
    private FormatDTO format;
    private AppUserDTO user;
    private LocalDateTime createdAt;
}

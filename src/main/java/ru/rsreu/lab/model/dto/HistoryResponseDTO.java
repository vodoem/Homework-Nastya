package ru.rsreu.lab.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoryResponseDTO {
    private Long id;
    private String originalText;
    private String formattedText;
    private Long formatId;
    private String formatType;
    private String userLogin;
    private LocalDateTime createdAt;
}


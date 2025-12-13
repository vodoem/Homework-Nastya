package ru.rsreu.lab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioRecognitionResponseDTO {
    private String recognizedText;
    private String formattedText; // если был указан formatId
    private Long processingTimeMs;
    private Long formatId;
}
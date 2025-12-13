package ru.rsreu.lab.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioRecognitionRequestDTO {
    private MultipartFile audioFile;
    private Long formatId; // опционально - сразу форматировать после распознавания
}
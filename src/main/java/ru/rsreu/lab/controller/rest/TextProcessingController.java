package ru.rsreu.lab.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.dto.HistoryDTO;
import ru.rsreu.lab.model.dto.request.HistoryRequestDTO;
import ru.rsreu.lab.model.dto.response.HistoryResponseDTO;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.service.HistoryService;
import ru.rsreu.lab.service.gigaChat.GigaChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/text")
@SecurityRequirement(name = "JWT")
@Tag(name = "Текст", description = "Обработка и форматирование текста")
public class TextProcessingController {

    private final GigaChatService gigaChatService;
    private final HistoryService historyService;

    @Operation(summary = "Отформатировать текст")
    @PostMapping("/format")
    public ResponseEntity<HistoryResponseDTO> formatText(@RequestBody HistoryRequestDTO requestDTO) {
        String formattedText = gigaChatService.formatText(
                requestDTO.getOriginalText(),
                requestDTO.getFormatId()
        );

        HistoryDTO historyDTO = HistoryDTO.builder()
                .originalText(requestDTO.getOriginalText())
                .formattedText(formattedText)
                .formatId(requestDTO.getFormatId())
                .build();

       History saved = historyService.save(historyDTO);

        HistoryResponseDTO response = HistoryResponseDTO.builder()
                .id(saved.getId())
                .originalText(saved.getOriginalText())
                .formattedText(saved.getFormattedText())
                .createdAt(saved.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }
}
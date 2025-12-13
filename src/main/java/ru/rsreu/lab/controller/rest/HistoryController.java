/*
package ru.rsreu.lab.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.dto.request.HistoryRequestDTO;
import ru.rsreu.lab.model.dto.response.FormatDTO;
import ru.rsreu.lab.model.dto.response.HistoryResponseDTO;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.service.FormatServiceImplJpa;
import ru.rsreu.lab.service.HistoryService;
import ru.rsreu.lab.model.dto.response.AppUserDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;
    private final FormatServiceImplJpa formatService;

    @GetMapping
    public ResponseEntity<List<HistoryResponseDTO>> getUserHistory(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Long formatId) {

        List<History> history = historyService.search(searchText, formatId);
        List<HistoryResponseDTO> response = history.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryResponseDTO> getHistoryById(@PathVariable Long id) {
        // Нужно добавить метод в сервисе для поиска по ID
        // Пока заглушка
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HistoryResponseDTO> createHistory(@RequestBody HistoryRequestDTO requestDTO) {
        // Конвертируем в HistoryDTO и сохраняем
        ru.rsreu.lab.model.dto.HistoryDTO historyDTO = ru.rsreu.lab.model.dto.HistoryDTO.builder()
                .originalText(requestDTO.getOriginalText())
                .formattedText(requestDTO.getFormattedText())
                .formatId(requestDTO.getFormatId())
                .build();

        History saved = historyService.save(historyDTO);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        // Логика удаления
        return ResponseEntity.noContent().build();
    }

    private HistoryResponseDTO toDTO(History history) {
        return HistoryResponseDTO.builder()
                .id(history.getId())
                .originalText(history.getOriginalText())
                .formattedText(history.getFormattedText())
                .format(FormatDTO.builder()
                        .id(history.getFormat().getId())
                        .formatType(history.getFormat().getFormatType())
                        .content(history.getFormat().getContent())
                        .build())
                .user(AppUserDTO.builder()
                        .id(history.getUser().getId())
                        .login(history.getUser().getLogin())
                        .build())
                .createdAt(history.getCreatedAt())
                .build();
    }
}*/

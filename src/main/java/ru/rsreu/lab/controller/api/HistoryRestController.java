package ru.rsreu.lab.controller.api;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.lab.model.dto.HistoryDTO;
import ru.rsreu.lab.model.dto.HistoryResponseDTO;
import ru.rsreu.lab.model.dto.TextForFormatingDTO;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.service.HistoryService;
import ru.rsreu.lab.service.gigaChat.GigaChatService;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryRestController {

    private final HistoryService historyService;
    private final GigaChatService gigaChatService;

    @GetMapping
    public List<HistoryResponseDTO> search(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Long formatId,
            @RequestParam(required = false) String login
    ) {
        return historyService.searchForUser(text, formatId, login)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @PostMapping
    public HistoryResponseDTO formatAndSave(
            @Valid @RequestBody TextForFormatingDTO request,
            @RequestHeader(value = "X-User-Login", required = false) String login,
            @RequestParam(value = "login", required = false) String loginParam
    ) {
        Long formatId = Long.parseLong(request.getFormat());
        String formattedText = gigaChatService.formatText(request.getText(), formatId);

        History saved = historyService.saveForUser(HistoryDTO.builder()
                .originalText(request.getText())
                .formattedText(formattedText)
                .formatId(formatId)
                .build(), login != null ? login : loginParam);

        return mapToDto(saved);
    }

    private HistoryResponseDTO mapToDto(History history) {
        return HistoryResponseDTO.builder()
                .id(history.getId())
                .originalText(history.getOriginalText())
                .formattedText(history.getFormattedText())
                .formatId(history.getFormat().getId())
                .formatType(history.getFormat().getFormatType())
                .userLogin(history.getUser().getLogin())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

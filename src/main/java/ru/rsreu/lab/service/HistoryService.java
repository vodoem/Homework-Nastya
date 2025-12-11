package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.dto.HistoryDTO;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.repository.HistoryRepositoryCustom;
import ru.rsreu.lab.service.AppUserService;
import ru.rsreu.lab.service.FormatService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepositoryCustom historyRepository;
    private final FormatService formatService; // теперь это интерфейс
    private final AppUserService userService; // теперь это интерфейс

    public History save(HistoryDTO dto) {
        Format format = formatService.findById(dto.getFormatId());
        AppUser user = userService.getCurrentUser();

        History history = History.builder()
                .originalText(dto.getOriginalText())
                .formattedText(dto.getFormattedText())
                .format(format)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return historyRepository.save(history);
    }

    public List<History> search(String text, Long formatId) {
        AppUser user = userService.getCurrentUser();

        if ((text == null || text.isBlank()) && formatId == null) {
            return historyRepository.findByUser(user);
        }

        if (text != null && !text.isBlank() && formatId != null) {
            return historyRepository.findByUserAndOriginalTextContainingAndFormatId(user, text, formatId);
        }

        if (text != null && !text.isBlank()) {
            return historyRepository.findByUserAndOriginalTextContaining(user, text);
        }

        return historyRepository.findByUserAndFormatId(user, formatId);
    }
}
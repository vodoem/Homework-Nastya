package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.dto.HistoryDTO;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.repository.jpa.HistoryJpaRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryJpaRepository historyRepository;
    private final FormatServiceImplJpa formatService; // теперь это интерфейс
    private final AppUserServiceImplJpa userService; // теперь это интерфейс

    public History save(HistoryDTO dto) {
        return saveForUser(dto, null);
    }

    public History saveForUser(HistoryDTO dto, String login) {
        Format format = formatService.findById(dto.getFormatId());
        AppUser user = resolveUser(login);

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
        return searchForUser(text, formatId, null);
    }

    public List<History> searchForUser(String text, Long formatId, String login) {
        AppUser user = resolveUser(login);

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

    private AppUser resolveUser(String login) {
        if (login != null && !login.isBlank()) {
            return userService.findByLogin(login)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь с логином " + login + " не найден"));
        }

        try {
            return userService.getCurrentUser();
        } catch (Exception ex) {
            throw new IllegalStateException("Не удалось определить текущего пользователя", ex);
        }
    }
}

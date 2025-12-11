package ru.rsreu.lab.repository;

import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.History;

import java.util.List;

public interface HistoryRepositoryCustom {
    History save(History entity);
    List<History> findByUser(AppUser user);
    List<History> findByUserAndFormatId(AppUser user, Long formatId);
    List<History> findByUserAndOriginalTextContainingAndFormatId(AppUser user, String text, Long formatId);
    List<History> findByUserAndOriginalTextContaining(AppUser user, String text);
}


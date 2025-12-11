package ru.rsreu.lab.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.repository.HistoryRepositoryCustom;

import java.util.List;

@Repository
@Profile("postgres")
@RequiredArgsConstructor
public class HistoryRepositoryJpaImpl implements HistoryRepositoryCustom {

    private final HistoryJpaRepository jpa;

    @Override
    public History save(History entity) {
        return jpa.save(entity);
    }

    @Override
    public List<History> findByUser(AppUser user) {
        return jpa.findByUser(user);
    }

    @Override
    public List<History> findByUserAndOriginalTextContaining(AppUser user, String text) {
        return jpa.findByUserAndOriginalTextContaining(user, text);
    }

    @Override
    public List<History> findByUserAndFormatId(AppUser user, Long formatId) {
        return jpa.findByUserAndFormatId(user, formatId);
    }

    @Override
    public List<History> findByUserAndOriginalTextContainingAndFormatId(AppUser user, String text, Long formatId) {
        return jpa.findByUserAndOriginalTextContainingAndFormatId(user, text, formatId);
    }
}

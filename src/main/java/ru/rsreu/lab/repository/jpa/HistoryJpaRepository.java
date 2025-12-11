package ru.rsreu.lab.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.History;

import java.util.List;


public interface HistoryJpaRepository extends JpaRepository<History, Long> {

    List<History> findByUser(AppUser user);

    List<History> findByUserAndFormatId(AppUser user, Long formatId);

    List<History> findByUserAndOriginalTextContaining(AppUser user, String text);

    List<History> findByUserAndOriginalTextContainingAndFormatId(
            AppUser user, String text, Long formatId
    );
}

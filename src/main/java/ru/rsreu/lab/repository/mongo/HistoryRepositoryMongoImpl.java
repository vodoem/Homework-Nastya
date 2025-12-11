package ru.rsreu.lab.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.rsreu.lab.model.document.HistoryDocument;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.repository.HistoryRepositoryCustom;

import java.util.List;
@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class HistoryRepositoryMongoImpl implements HistoryRepositoryCustom {

    private final HistoryMongoRepository mongo;

    @Override
    public History save(History entity) {
        HistoryDocument doc = HistoryDocument.builder()
                .originalText(entity.getOriginalText())
                .formattedText(entity.getFormattedText())
                .formatType(entity.getFormat().getFormatType())
                .userLogin(entity.getUser().getLogin())
                .createdAt(entity.getCreatedAt())
                .build();

        mongo.save(doc);

        return entity;
    }

    @Override
    public List<History> findByUser(AppUser user) {
        return mongo.findByUserLogin(user.getLogin()).stream()
                .map(doc -> History.builder()
                        .originalText(doc.getOriginalText())
                        .formattedText(doc.getFormattedText())
                        .createdAt(doc.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<History> findByUserAndOriginalTextContaining(AppUser user, String text) {
        return mongo.findByUserLogin(user.getLogin()).stream()
                .filter(doc -> doc.getOriginalText().contains(text))
                .map(doc -> History.builder()
                        .originalText(doc.getOriginalText())
                        .formattedText(doc.getFormattedText())
                        .createdAt(doc.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<History> findByUserAndFormatId(AppUser user, Long formatId) {
        return mongo.findByUserLogin(user.getLogin()).stream()
                .filter(doc -> doc.getFormatType().hashCode() == formatId.hashCode()) // простая проверка, можешь заменить на маппинг
                .map(doc -> History.builder()
                        .originalText(doc.getOriginalText())
                        .formattedText(doc.getFormattedText())
                        .createdAt(doc.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<History> findByUserAndOriginalTextContainingAndFormatId(AppUser user, String text, Long formatId) {
        return mongo.findByUserLogin(user.getLogin()).stream()
                .filter(doc -> doc.getOriginalText().contains(text))
                .filter(doc -> doc.getFormatType().hashCode() == formatId.hashCode())
                .map(doc -> History.builder()
                        .originalText(doc.getOriginalText())
                        .formattedText(doc.getFormattedText())
                        .createdAt(doc.getCreatedAt())
                        .build())
                .toList();
    }
}

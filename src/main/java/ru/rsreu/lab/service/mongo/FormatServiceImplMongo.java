/*
package ru.rsreu.lab.service.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.document.FormatDocument;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.repository.mongo.FormatRepository;
import ru.rsreu.lab.service.FormatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "app.db-type", havingValue = "mongo")
@RequiredArgsConstructor
public class FormatServiceImplMongo implements FormatService {
    private final FormatRepository formatRepository;

    @Override
    public List<Format> findAll() {
        return formatRepository.findAll().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Format findById(Long id) {
        return formatRepository.findById(String.valueOf(id))
                .map(this::toEntity)
                .orElseThrow(() -> new RuntimeException("Формат не найден"));
    }

    private Format toEntity(FormatDocument document) {
        return Format.builder()
                .id(Long.parseLong(document.getId()))
                .formatType(document.getFormatType())
                .content(document.getContent())
                .build();
    }
}*/

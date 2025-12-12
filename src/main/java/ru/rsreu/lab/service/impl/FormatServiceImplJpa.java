package ru.rsreu.lab.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.repository.jpa.FormatRepository;
import ru.rsreu.lab.service.FormatService;

import java.util.List;

@Service
@ConditionalOnProperty(name = "app.db-type", havingValue = "postgres")
@RequiredArgsConstructor
public class FormatServiceImplJpa implements FormatService {
    private final FormatRepository formatRepository;

    @Override
    public List<Format> findAll() {
        return formatRepository.findAll();
    }

    @Override
    public Format findById(Long id) {
        return formatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Формат не найден"));
    }
}
package ru.rsreu.lab.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.repository.jpa.FormatRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class FormatServiceImplJpa {
    private final FormatRepository formatRepository;

    public List<Format> findAll() {
        return formatRepository.findAll();
    }

    public Format findById(Long id) {
        return formatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Формат не найден"));
    }
}
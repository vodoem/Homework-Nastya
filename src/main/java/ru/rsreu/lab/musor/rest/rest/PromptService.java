/*
package ru.rsreu.lab.service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.entity.Prompt;
import ru.rsreu.lab.repository.PromptRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    public List<Prompt> getByFormatType(String formatType) {
        return promptRepository.findByFormatType(formatType);
    }

    public Prompt save(Prompt prompt) {
        return promptRepository.save(prompt);
    }
}
*/

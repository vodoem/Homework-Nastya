package ru.rsreu.lab.service.gigaChat;

import lombok.RequiredArgsConstructor;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.service.FormatServiceImplJpa;
import ru.rsreu.lab.service.LLMCacheService;


import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
public class GigaChatMockService implements GigaChatService {

    private final FormatServiceImplJpa formatService;
    private final LLMCacheService cacheService;

    private final Map<String, String> responseTemplates = Map.of(
            "статья", """
            # Заголовок статьи
            {{text}}
            
            ## Основной раздел
            Это автоматически сгенерированная статья на основе вашего текста.
            
            ### Подраздел
            Текст был обработан в mock-режиме в {{timestamp}}.
            
            *С уважением, Mock Service*
            """,

            "список", """
            ## Список задач:
            1. Первая задача: обработать текст
            2. Вторая задача: отформатировать как список
            3. Третья задача: добавить структуру
            
            ### Исходный текст:
            {{text}}
            
            📅 Создано: {{timestamp}}
            """,

            "default", """
            📝 Обработанный текст:
            
            {{text}}
            
            ⚠️ Внимание: работа в mock-режиме!
            🔧 Формат: {{format}}
            🕐 Время: {{timestamp}}
            """
    );

    @Override
    public String formatText(String text, Long formatId) {
        // Получаем формат
        Format format = formatService.findById(formatId);
        String formatType = format.getFormatType();

        // Генерируем ключ кэша (как в реальном сервисе)
        String cacheKey = cacheService.generateCacheKey(text, formatId, format.getContent());

        // Проверяем кэш
        var cachedResult = cacheService.getFromCache(cacheKey);
        if (cachedResult.isPresent()) {
            return cachedResult.get().getFormattedText();
        }

        // Генерируем mock-ответ
        String response = generateMockResponse(text, formatType);

        // Сохраняем в кэш (чтобы тестировать кэширование)
        cacheService.saveToCache(
                cacheKey,
                text,
                response,
                formatId,
                formatType,
                "MOCK_PROMPT",
                "gigachat-mock",
                estimateTokens(response)
        );

        return response;
    }

    private String generateMockResponse(String text, String formatType) {
        String template = responseTemplates.getOrDefault(formatType, responseTemplates.get("default"));

        return template
                .replace("{{text}}", text)
                .replace("{{format}}", formatType)
                .replace("{{timestamp}}", LocalDateTime.now().toString())
                .replace("{{originalText}}", text.substring(0, Math.min(text.length(), 100)) + "...");
    }

    private Integer estimateTokens(String text) {
        // Примерная оценка токенов (как в реальном сервисе)
        return (int) Math.ceil(text.length() / 4.0);
    }
}
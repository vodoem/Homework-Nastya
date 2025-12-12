package ru.rsreu.lab.service.gigaChat;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;
import org.springframework.beans.factory.annotation.Value;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.service.FormatServiceImplJpa;
import ru.rsreu.lab.service.LLMCacheService;

import java.time.Duration;
import java.time.Instant;


public class GigaChatServiceImpl implements GigaChatService {
    private final GigaChatClient client;
    private final FormatServiceImplJpa formatService;
    private final LLMCacheService cacheService; // Добавляем кэш сервис

    public GigaChatServiceImpl(@Value("${gigachat.authKey}") String authKey,
                           FormatServiceImplJpa formatService,
                           LLMCacheService cacheService) {
        this.client = GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(authKey)
                                .build())
                        .build())
                .build();
        this.formatService = formatService;
        this.cacheService = cacheService;
    }

    @Override
    public String formatText(String text, Long formatId) {
        Instant start = Instant.now();

        // 1. Получаем формат и промпт
        Format format = formatService.findById(formatId);
        String prompt = format.getContent(); // промпт из БД

        // 2. Генерируем ключ кэша
        String cacheKey = cacheService.generateCacheKey(text, formatId, prompt);

        // 3. Проверяем кэш
        var cachedResult = cacheService.getFromCache(cacheKey);
        if (cachedResult.isPresent()) {
            return cachedResult.get().getFormattedText();
        }

        // 4. Если нет в кэше - вызываем API
        String content = format.getFormatType() + " " + text;

        CompletionRequest request = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(content)
                        .role(ChatMessageRole.USER)
                        .build())
                .build();

        CompletionResponse response = client.completions(request);
        String result = response.choices().getFirst().message().content();

        Duration apiDuration = Duration.between(start, Instant.now());

        // 5. Сохраняем в кэш для будущих запросов
        cacheService.saveToCache(
                cacheKey,
                text,
                result,
                formatId,
                format.getFormatType(),
                prompt,
                "GigaChat",
                estimateTokens(result) // Примерная оценка токенов
        );

        return result;
    }

    /**
     * Примерная оценка количества токенов
     * (В реальности лучше использовать точный расчет или данные от API)
     */
    private Integer estimateTokens(String text) {
        // Примерно 1 токен = 4 символа для русского языка
        return (int) Math.ceil(text.length() / 4.0);
    }

}
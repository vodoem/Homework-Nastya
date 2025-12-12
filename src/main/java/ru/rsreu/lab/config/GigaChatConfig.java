package ru.rsreu.lab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.rsreu.lab.service.FormatServiceImplJpa;
import ru.rsreu.lab.service.gigaChat.GigaChatService;
import ru.rsreu.lab.service.gigaChat.GigaChatServiceImpl;
import ru.rsreu.lab.service.LLMCacheService;
import ru.rsreu.lab.service.gigaChat.GigaChatMockService;

@Configuration
public class GigaChatConfig {

    @Bean
    @Profile("dev")
    public GigaChatService realGigaChatService(
            @Value("${gigachat.authKey}") String authKey,
            FormatServiceImplJpa formatService,
            LLMCacheService cacheService) {

        return new GigaChatServiceImpl(authKey, formatService, cacheService);
    }

    @Bean
    @Profile("mock")
    public GigaChatMockService mockGigaChatService(
            FormatServiceImplJpa formatService,
            LLMCacheService cacheService,
            AppConfig appProperties) {

        return new GigaChatMockService(formatService, cacheService);
    }
}
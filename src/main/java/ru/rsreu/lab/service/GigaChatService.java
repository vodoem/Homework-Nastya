package ru.rsreu.lab.service;

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
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.service.FormatService;

@Service
public class GigaChatService {
    private final GigaChatClient client;
    private final FormatService formatService;

    public GigaChatService(@Value("${gigachat.authKey}") String authKey,
                           FormatService formatService) { // <-- ИНТЕРФЕЙС
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
    }

    public String formatText(String text, Long formatId) {
        Format format = formatService.findById(formatId);

        String content = format.getFormatType() + " " + text;

        CompletionRequest request = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(content)
                        .role(ChatMessageRole.USER)
                        .build())
                .build();


        CompletionResponse response = client.completions(request);

        return response.choices().getFirst().message().content();
    }
}

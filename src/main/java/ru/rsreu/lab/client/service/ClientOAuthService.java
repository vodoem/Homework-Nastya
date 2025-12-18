package ru.rsreu.lab.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.lab.client.model.OAuthTokenResponse;

import java.time.Instant;

@Service
@Profile("client")
@RequiredArgsConstructor
public class ClientOAuthService {

    private final RestTemplate restTemplate;

    @Value("${client.oauth.token-uri}")
    private String tokenUri;

    @Value("${client.oauth.client-id}")
    private String clientId;

    @Value("${client.oauth.client-secret}")
    private String clientSecret;

    @Value("${client.oauth.scope}")
    private String scope;

    private String cachedAccessToken;
    private Instant expiresAt;

    public synchronized String getAccessToken() {
        if (cachedAccessToken != null && expiresAt != null && Instant.now().isBefore(expiresAt)) {
            return cachedAccessToken;
        }

        OAuthTokenResponse tokenResponse = requestNewToken();
        if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
            throw new IllegalStateException("Не удалось получить OAuth токен");
        }

        cachedAccessToken = tokenResponse.getAccessToken();
        long lifetime = tokenResponse.getExpiresIn() > 30 ? tokenResponse.getExpiresIn() - 30 : tokenResponse.getExpiresIn();
        expiresAt = Instant.now().plusSeconds(lifetime);
        return cachedAccessToken;
    }

    private OAuthTokenResponse requestNewToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(tokenUri, request, OAuthTokenResponse.class);
    }
}

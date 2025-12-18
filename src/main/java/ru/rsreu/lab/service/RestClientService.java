package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.lab.model.dto.request.HistoryRequestDTO;
import ru.rsreu.lab.model.dto.request.JwtRequestDTO;
import ru.rsreu.lab.model.dto.response.FormatDTO;
import ru.rsreu.lab.model.dto.response.HistoryResponseDTO;
import ru.rsreu.lab.model.dto.response.JwtResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestClientService {

    private final RestTemplate restTemplate;

    @Value("${rest.client.base-url:http://localhost:8080}")
    private String apiBaseUrl;

    public JwtResponseDTO authenticate(JwtRequestDTO request) {
        String url = apiBaseUrl + "/api/auth/login";
        try {
            ResponseEntity<JwtResponseDTO> response = restTemplate.postForEntity(url, request, JwtResponseDTO.class);
            return Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new IllegalStateException("Пустой ответ от сервера авторизации"));
        } catch (RestClientException ex) {
            log.error("Ошибка при аутентификации", ex);
            throw new IllegalStateException("Не удалось выполнить вход: " + ex.getMessage());
        }
    }

    public JwtResponseDTO register(JwtRequestDTO request) {
        String url = apiBaseUrl + "/api/auth/register";
        try {
            ResponseEntity<JwtResponseDTO> response = restTemplate.postForEntity(url, request, JwtResponseDTO.class);
            return Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new IllegalStateException("Пустой ответ при регистрации"));
        } catch (RestClientException ex) {
            log.error("Ошибка при регистрации", ex);
            throw new IllegalStateException("Не удалось зарегистрироваться: " + ex.getMessage());
        }
    }

    public List<FormatDTO> loadFormats(String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<FormatDTO>> response = restTemplate.exchange(
                    apiBaseUrl + "/api/formats",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return Optional.ofNullable(response.getBody()).orElse(Collections.emptyList());
        } catch (RestClientException ex) {
            log.error("Ошибка при получении форматов", ex);
            throw new IllegalStateException("Не удалось загрузить форматы: " + ex.getMessage());
        }
    }

    public HistoryResponseDTO formatText(String accessToken, HistoryRequestDTO request) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HistoryRequestDTO> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<HistoryResponseDTO> response = restTemplate.exchange(
                    apiBaseUrl + "/api/text/format",
                    HttpMethod.POST,
                    entity,
                    HistoryResponseDTO.class
            );

            return Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new IllegalStateException("Пустой ответ от сервиса форматирования"));
        } catch (RestClientException ex) {
            log.error("Ошибка при отправке текста на форматирование", ex);
            throw new IllegalStateException("Не удалось отправить текст: " + ex.getMessage());
        }
    }

    private HttpHeaders createAuthHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}

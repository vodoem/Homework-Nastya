package ru.rsreu.lab.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.rsreu.lab.model.dto.HistoryResponseDTO;
import ru.rsreu.lab.model.dto.TextForFormatingDTO;
import ru.rsreu.lab.model.entity.Format;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Profile("client")
@RequiredArgsConstructor
public class ClientApiService {

    private final RestTemplate restTemplate;
    private final ClientOAuthService clientOAuthService;

    @Value("${client.server-url}")
    private String serverUrl;

    public List<Format> loadFormats() {
        HttpHeaders headers = authHeaders();
        ResponseEntity<Format[]> response = restTemplate.exchange(
                serverUrl + "/api/formats",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Format[].class
        );
        Format[] formats = response.getBody();
        return formats == null ? Collections.emptyList() : Arrays.asList(formats);
    }

    public Optional<HistoryResponseDTO> sendFormatRequest(TextForFormatingDTO dto, String login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Login", login);
        headers.setBearerAuth(clientOAuthService.getAccessToken());

        HttpEntity<TextForFormatingDTO> entity = new HttpEntity<>(dto, headers);
        HistoryResponseDTO response = restTemplate.postForObject(
                serverUrl + "/api/history",
                entity,
                HistoryResponseDTO.class
        );

        return Optional.ofNullable(response);
    }

    public List<HistoryResponseDTO> loadHistory(String login) {
        HttpHeaders headers = authHeaders();
        ResponseEntity<HistoryResponseDTO[]> response = restTemplate.exchange(
                serverUrl + "/api/history?login={login}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                HistoryResponseDTO[].class,
                login
        );
        HistoryResponseDTO[] history = response.getBody();
        return history == null ? Collections.emptyList() : Arrays.asList(history);
    }

    private HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(clientOAuthService.getAccessToken());
        return headers;
    }
}

package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
@RequiredArgsConstructor
public class RestClientService {

    private final RestTemplate restTemplate;

    @Value("${app.api-base-url}")
    private String apiBaseUrl;

    public List<Format> loadFormats() {
        Format[] formats = restTemplate.getForObject(apiBaseUrl + "/api/formats", Format[].class);
        return formats == null ? Collections.emptyList() : Arrays.asList(formats);
    }

    public Optional<HistoryResponseDTO> sendFormattingRequest(TextForFormatingDTO dto, String login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Login", login);

        HttpEntity<TextForFormatingDTO> request = new HttpEntity<>(dto, headers);
        HistoryResponseDTO response = restTemplate.postForObject(
                apiBaseUrl + "/api/history",
                request,
                HistoryResponseDTO.class
        );

        return Optional.ofNullable(response);
    }

    public List<HistoryResponseDTO> loadHistory(String login) {
        HistoryResponseDTO[] history = restTemplate.getForObject(
                apiBaseUrl + "/api/history?login={login}",
                HistoryResponseDTO[].class,
                login
        );

        return history == null ? Collections.emptyList() : Arrays.asList(history);
    }
}


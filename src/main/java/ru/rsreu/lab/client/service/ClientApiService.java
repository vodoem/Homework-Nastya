package ru.rsreu.lab.client.service;

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
public class ClientApiService {

    private final RestTemplate restTemplate;

    @Value("${client.server-url}")
    private String serverUrl;

    public List<Format> loadFormats() {
        Format[] formats = restTemplate.getForObject(serverUrl + "/api/formats", Format[].class);
        return formats == null ? Collections.emptyList() : Arrays.asList(formats);
    }

    public Optional<HistoryResponseDTO> sendFormatRequest(TextForFormatingDTO dto, String login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Login", login);

        HttpEntity<TextForFormatingDTO> entity = new HttpEntity<>(dto, headers);
        HistoryResponseDTO response = restTemplate.postForObject(
                serverUrl + "/api/history",
                entity,
                HistoryResponseDTO.class
        );

        return Optional.ofNullable(response);
    }

    public List<HistoryResponseDTO> loadHistory(String login) {
        HistoryResponseDTO[] history = restTemplate.getForObject(
                serverUrl + "/api/history?login={login}",
                HistoryResponseDTO[].class,
                login
        );
        return history == null ? Collections.emptyList() : Arrays.asList(history);
    }
}


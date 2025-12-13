/*
package ru.rsreu.lab.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.document.LLMCacheDocument;
import ru.rsreu.lab.model.dto.CacheStatsResponseDTO;
import ru.rsreu.lab.service.LLMCacheService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cache")
public class CacheController {

    private final LLMCacheService cacheService;

    @GetMapping("/stats")
    public ResponseEntity<CacheStatsResponseDTO> getCacheStats() {
        ru.rsreu.lab.model.dto.CacheStatsDTO stats = cacheService.getCacheStats();

        // Считаем дополнительную статистику
        Map<String, Long> entriesByFormat = cacheService.getAllCacheEntries().stream()
                .collect(Collectors.groupingBy(
                        LLMCacheDocument::getFormatType,
                        Collectors.counting()
                ));

        CacheStatsResponseDTO response = CacheStatsResponseDTO.builder()
                .totalEntries(stats.getTotalEntries())
                .totalHits(stats.getTotalHits())
                .hitRate(stats.getHitRate())
                .entriesByFormat(entriesByFormat)
                .estimatedMoneySaved(calculateMoneySaved(stats))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/entries")
    public ResponseEntity<List<LLMCacheDocument>> getAllCacheEntries() {
        List<LLMCacheDocument> entries = cacheService.getAllCacheEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/entries/by-format/{formatId}")
    public ResponseEntity<List<LLMCacheDocument>> getCacheEntriesByFormat(@PathVariable Long formatId) {
        List<LLMCacheDocument> entries = cacheService.getByFormatId(formatId);
        return ResponseEntity.ok(entries);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCache() {
        cacheService.clearAllCache();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Void> cleanupOldCache() {
        cacheService.cleanOldCache();
        return ResponseEntity.noContent().build();
    }

    private Long calculateMoneySaved(ru.rsreu.lab.model.dto.CacheStatsDTO stats) {
        // Примерный расчет: 1000 токенов = 0.5 рубля
        // Каждое попадание в кэш экономит ~100 токенов
        long estimatedTokensSaved = stats.getTotalHits() * 100;
        return estimatedTokensSaved / 2; // Примерно в копейках
    }
}*/

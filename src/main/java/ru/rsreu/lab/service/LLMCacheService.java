package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.rsreu.lab.model.document.LLMCacheDocument;
import ru.rsreu.lab.model.dto.CacheStatsDTO;
import ru.rsreu.lab.repository.mongo.LLMCacheMongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMCacheService {

    private final LLMCacheMongoRepository cacheRepository;

    public String generateCacheKey(String originalText, Long formatId, String prompt) {
        String dataToHash = originalText + "|" + formatId + "|" + prompt;
        return DigestUtils.md5DigestAsHex(dataToHash.getBytes());
    }

    public Optional<LLMCacheDocument> getFromCache(String cacheKey) {
        Optional<LLMCacheDocument> cached = cacheRepository.findByHash(cacheKey);

        cached.ifPresent(doc -> {
            // Увеличиваем счетчик использования
            doc.setHitCount(doc.getHitCount() + 1);
            cacheRepository.save(doc);
            log.debug("Cache hit for key: {}. Total hits: {}", cacheKey, doc.getHitCount());
        });

        return cached;
    }

    public LLMCacheDocument saveToCache(
            String cacheKey,
            String originalText,
            String formattedText,
            Long formatId,
            String formatType,
            String promptUsed,
            String model,
            Integer tokensUsed
    ) {
        LLMCacheDocument cacheDoc = LLMCacheDocument.builder()
                .hash(cacheKey)
                .originalText(originalText)
                .formattedText(formattedText)
                .formatId(formatId)
                .formatType(formatType)
                .promptUsed(promptUsed)
                .model(model)
                .tokensUsed(tokensUsed)
                .createdAt(LocalDateTime.now())
                .hitCount(0)
                .build();

        LLMCacheDocument saved = cacheRepository.save(cacheDoc);
        log.info("Saved to LLM cache. Key: {}, Format: {}", cacheKey, formatType);

        return saved;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanOldCache() {
        LocalDateTime monthAgo = LocalDateTime.now().minusDays(30);

        // Сначала считаем сколько удалим
        long countToDelete = cacheRepository.countByCreatedAtBefore(monthAgo);

        if (countToDelete > 0) {
            // Удаляем старые записи
            long deleted = cacheRepository.deleteByCreatedAtBefore(monthAgo);
            log.info("Cleaned {} old cache entries (older than 30 days)", deleted);
        }
    }

    public CacheStatsDTO getCacheStats() {
        long totalEntries = cacheRepository.count();

        // Считаем общее количество попаданий
        long totalHits = cacheRepository.findAll()
                .stream()
                .mapToLong(LLMCacheDocument::getHitCount)
                .sum();

        return CacheStatsDTO.builder()
                .totalEntries(totalEntries)
                .totalHits(totalHits)
                .hitRate(totalEntries > 0 ? (double) totalHits / totalEntries : 0)
                .build();
    }

    // ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ ДЛЯ ОТЛАДКИ:

    public void clearAllCache() {
        cacheRepository.deleteAll();
        log.info("Cache cleared completely");
    }

    public List<LLMCacheDocument> getAllCacheEntries() {
        return cacheRepository.findAll();
    }

    public List<LLMCacheDocument> getByFormatId(Long formatId) {
        return cacheRepository.findByFormatId(formatId);
    }
}
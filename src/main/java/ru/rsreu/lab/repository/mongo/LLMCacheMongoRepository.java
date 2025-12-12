package ru.rsreu.lab.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rsreu.lab.model.document.LLMCacheDocument;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LLMCacheMongoRepository extends MongoRepository<LLMCacheDocument, String> {

    @Query("{ 'hash': ?0 }")
    Optional<LLMCacheDocument> findByHash(String hash);

    // УБИРАЕМ ЭТОТ МЕТОД - он ищет ОДНУ запись по formatId, а у тебя может быть много
    // Optional<LLMCacheDocument> findByFormatId(Long formatId);

    // Вместо него можно сделать поиск ВСЕХ записей по formatId
    List<LLMCacheDocument> findByFormatId(Long formatId);

    long countByFormatType(String formatType);

    // Этот метод не нужен - Spring Data не поддерживает delete в @Query так
    // @Query(value = "{ 'createdAt': { $lt: ?0 } }", delete = true)
    // void deleteOlderThan(java.time.LocalDateTime date);

    // Вместо него используем встроенные методы:
    long deleteByCreatedAtBefore(LocalDateTime date);

    long countByCreatedAtBefore(LocalDateTime date);

    List<LLMCacheDocument> findByCreatedAtBefore(LocalDateTime date);

    // Этот метод тоже странный - он вернет только поле hitCount, остальные null
    // @Query(value = "{}", fields = "{ 'hitCount' : 1 }")
    // List<LLMCacheDocument> findAllWithHitCount();

    // Если нужно только hitCount - лучше так:
    @Query(value = "{}", fields = "{ 'hitCount' : 1, 'formatType' : 1 }")
    List<LLMCacheDocument> findAllProjected();
}
package ru.rsreu.lab.model.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "llm_cache")
@Data
@Builder
public class LLMCacheDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String hash; // текст + formatId + prompt

    private String originalText;
    private String formattedText;
    private Long formatId;
    private String formatType;

    @TextIndexed // для полнотекстового поиска
    private String promptUsed; // промпт из таблицы Format

    private String model;
    private Integer tokensUsed;
    private LocalDateTime createdAt;
    private Integer hitCount = 0; // сколько раз использовали
}

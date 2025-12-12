package ru.rsreu.lab.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CacheStatsDTO {
    private Long totalEntries;
    private Long totalHits;
    private Double hitRate;
    private Map<String, Long> entriesByFormat;
}
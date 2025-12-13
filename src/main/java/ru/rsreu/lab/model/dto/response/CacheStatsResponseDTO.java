package ru.rsreu.lab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheStatsResponseDTO {
    private Long totalEntries;
    private Long totalHits;
    private Double hitRate;
    private Map<String, Long> entriesByFormat;
    private Long estimatedMoneySaved; // в копейках

    public String getHitRatePercentage() {
        return String.format("%.2f%%", (hitRate != null ? hitRate * 100 : 0));
    }
}
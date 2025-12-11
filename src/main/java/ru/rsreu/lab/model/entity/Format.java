package ru.rsreu.lab.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Format")
public class Format {
    public static final String DEFAULT_GENERATOR = "default_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DEFAULT_GENERATOR)
    private Long id;

    private String formatType;

    private String content;
}
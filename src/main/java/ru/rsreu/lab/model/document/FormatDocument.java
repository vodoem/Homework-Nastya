package ru.rsreu.lab.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "formats")
public class FormatDocument {
    @Id
    private String id;

    private String formatType;
    private String content;
}

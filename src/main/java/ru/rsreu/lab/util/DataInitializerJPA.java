package ru.rsreu.lab.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.rsreu.lab.model.entity.Format;

@Component
@RequiredArgsConstructor
@Profile("postgres")
public class DataInitializerJPA implements CommandLineRunner {

    private final ru.rsreu.lab.repository.jpa.FormatRepository formatRepository;

    @Override
    public void run(String... args) {
        if (formatRepository.count() == 0) {
            Format article = Format.builder()
                    .formatType("статья")
                    .content("...") // оставляем текст
                    .build();

            Format checklist = Format.builder()
                    .formatType("список")
                    .content("...")
                    .build();

            formatRepository.save(article);
            formatRepository.save(checklist);

            System.out.println("Default formats created for JPA!");
        }
    }
}

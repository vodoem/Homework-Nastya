package ru.rsreu.lab.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.repository.jpa.FormatRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FormatRepository formatRepository;

    @Override
    public void run(String... args) {
        if (formatRepository.count() == 0) {

            Format article = Format.builder()
                    .formatType("статья")
                    .content("Преврати этот текст в статью. Не добавляй новые слова, не удаляй существующие слова, можно только менять окончания там, где это требуется. Расставь знаки препинания. Не придумывай.")
                    .build();

            Format checklist = Format.builder()
                    .formatType("список")
                    .content("Сделай чек-лист из этого текста. Не добавляй новые слова, не удаляй существующие слова, можно только менять окончания там, где это требуется. Используй только введённые слова. Никаких новых. Не пиши свои рассуждения. Только список. Без лишних слов.")
                    .build();

            formatRepository.save(article);
            formatRepository.save(checklist);

            System.out.println("Default formats created!");
        }
    }
}

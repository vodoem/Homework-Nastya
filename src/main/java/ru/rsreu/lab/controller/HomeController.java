package ru.rsreu.lab.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.rsreu.lab.model.dto.HistoryDTO;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.service.FormatService;
import ru.rsreu.lab.service.GigaChatService;

import ru.rsreu.lab.model.dto.TextForFormatingDTO;
import ru.rsreu.lab.service.HistoryService;
import ru.rsreu.lab.service.SpeechRecognitionService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/secure")
public class HomeController {

    private final HistoryService historyService;
    private final FormatService formatService;
    private final SpeechRecognitionService speechRecognitionService;
    private final GigaChatService gigaChatService;

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("textForFormatingDTO", new TextForFormatingDTO());
        model.addAttribute("formats", formatService.findAll());
        return "home";
    }


    @PostMapping("/format")
    public String format(@Valid TextForFormatingDTO dto, Model model) {
        String formattedText = gigaChatService.formatText(dto.getText(), Long.parseLong(dto.getFormat()));

        historyService.save(HistoryDTO.builder()
                .originalText(dto.getText())
                .formattedText(formattedText)
                .formatId(Long.parseLong(dto.getFormat()))
                .build()
        );

        model.addAttribute("result", formattedText);
        model.addAttribute("textForFormatingDTO", dto);

        model.addAttribute("formats", formatService.findAll());

        return "home";
    }



    @PostMapping("/recognize")
    public String recognizeSpeech(@RequestParam("file") MultipartFile file, Model model) {
        TextForFormatingDTO dto = new TextForFormatingDTO();

        try {
            String recognized = speechRecognitionService.recognize(file);
            dto.setText(recognized);
        } catch (Exception e) {
            dto.setText("Ошибка распознавания: " + e.getMessage());
        }

        model.addAttribute("textForFormatingDTO", dto);
        return "home";
    }




}

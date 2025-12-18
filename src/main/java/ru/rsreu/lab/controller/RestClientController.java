package ru.rsreu.lab.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.lab.model.dto.TextForFormatingDTO;
import ru.rsreu.lab.service.RestClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/secure/rest-client")
public class RestClientController {

    private final RestClientService restClientService;

    @GetMapping
    public String showClientPage(Model model, Authentication authentication) {
        model.addAttribute("textForFormatingDTO", new TextForFormatingDTO());
        model.addAttribute("formats", restClientService.loadFormats());
        model.addAttribute("history", restClientService.loadHistory(authentication.getName()));
        return "rest-client";
    }

    @PostMapping
    public String formatThroughApi(@Valid TextForFormatingDTO dto,
                                   BindingResult bindingResult,
                                   Model model,
                                   Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("formats", restClientService.loadFormats());
            model.addAttribute("history", restClientService.loadHistory(authentication.getName()));
            return "rest-client";
        }

        restClientService.sendFormattingRequest(dto, authentication.getName())
                .ifPresent(response -> model.addAttribute("formattedResult", response));

        model.addAttribute("textForFormatingDTO", dto);
        model.addAttribute("formats", restClientService.loadFormats());
        model.addAttribute("history", restClientService.loadHistory(authentication.getName()));
        return "rest-client";
    }
}


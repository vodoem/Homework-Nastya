package ru.rsreu.lab.client.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsreu.lab.client.service.ClientApiService;
import ru.rsreu.lab.model.dto.TextForFormatingDTO;

@Controller
@Profile("client")
@RequiredArgsConstructor
public class ClientPageController {

    private final ClientApiService clientApiService;

    @GetMapping("/")
    public String showForm(Model model, @RequestParam(defaultValue = "admin") String login) {
        model.addAttribute("textForFormatingDTO", new TextForFormatingDTO());
        model.addAttribute("formats", clientApiService.loadFormats());
        model.addAttribute("history", clientApiService.loadHistory(login));
        model.addAttribute("login", login);
        return "client/index";
    }

    @PostMapping("/format")
    public String format(
            @Valid TextForFormatingDTO dto,
            BindingResult bindingResult,
            Model model,
            @RequestParam(defaultValue = "admin") String login
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formats", clientApiService.loadFormats());
            model.addAttribute("history", clientApiService.loadHistory(login));
            model.addAttribute("login", login);
            return "client/index";
        }

        clientApiService.sendFormatRequest(dto, login)
                .ifPresent(res -> model.addAttribute("formattedResult", res));

        model.addAttribute("formats", clientApiService.loadFormats());
        model.addAttribute("history", clientApiService.loadHistory(login));
        model.addAttribute("login", login);
        model.addAttribute("textForFormatingDTO", dto);
        return "client/index";
    }
}

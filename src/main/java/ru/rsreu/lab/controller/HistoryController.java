package ru.rsreu.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsreu.lab.model.entity.History;
import ru.rsreu.lab.service.FormatServiceImplJpa;
import ru.rsreu.lab.service.HistoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/secure/history")
public class HistoryController {

    private final HistoryService historyService;
    private final FormatServiceImplJpa formatService;

    @GetMapping
    public String historyPage(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Long formatId,
            Model model
    ) {

        List<History> list = historyService.search(text, formatId);

        model.addAttribute("history", list);
        model.addAttribute("formats", formatService.findAll());
        model.addAttribute("text", text);
        model.addAttribute("formatId", formatId);

        return "history";
    }
}

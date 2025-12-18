package ru.rsreu.lab.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.service.FormatServiceImplJpa;

import java.util.List;

@RestController
@RequestMapping("/api/formats")
@RequiredArgsConstructor
public class FormatRestController {

    private final FormatServiceImplJpa formatService;

    @GetMapping
    public List<Format> getAll() {
        return formatService.findAll();
    }

    @GetMapping("/{id}")
    public Format getById(@PathVariable Long id) {
        return formatService.findById(id);
    }
}


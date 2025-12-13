package ru.rsreu.lab.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.dto.response.FormatDTO;
import ru.rsreu.lab.model.entity.Format;
import ru.rsreu.lab.service.FormatServiceImplJpa;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/formats")
public class FormatController {

    private final FormatServiceImplJpa formatService;

    @GetMapping
    public ResponseEntity<List<FormatDTO>> getAllFormats() {
        List<FormatDTO> formats = formatService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(formats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormatDTO> getFormatById(@PathVariable Long id) {
        Format format = formatService.findById(id);
        return ResponseEntity.ok(toDTO(format));
    }

    private FormatDTO toDTO(Format format) {
        return FormatDTO.builder()
                .id(format.getId())
                .formatType(format.getFormatType())
                .content(format.getContent())
                .build();
    }
}
/*
package ru.rsreu.lab.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.entity.History;
import ru.rsreu.lab.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{userId}")
    public List<History> getUserHistory(@PathVariable String userId) {
        return historyService.getUserHistory(userId);
    }

    @PostMapping
    public History save(@RequestBody History history) {
        return historyService.save(history);
    }
}
*/

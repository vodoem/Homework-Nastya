package ru.rsreu.lab.service;

import ru.rsreu.lab.model.entity.Format;

import java.util.List;

public interface FormatService {
    List<Format> findAll();
    Format findById(Long id);
}
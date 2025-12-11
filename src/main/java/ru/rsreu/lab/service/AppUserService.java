package ru.rsreu.lab.service;

import ru.rsreu.lab.model.entity.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser getCurrentUser();
    Optional<AppUser> findByLogin(String login);
    AppUser save(AppUser user);
}
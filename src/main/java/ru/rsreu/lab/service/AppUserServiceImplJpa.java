package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.repository.jpa.AppUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImplJpa {
    private final AppUserRepository appUserRepository;

    public AppUser getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public Optional<AppUser> findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }

    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser getById(long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
package ru.rsreu.lab.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
        return appUserRepository.findByLogin(login).orElseThrow();
    }

    public Optional<AppUser> findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }

    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }
}
package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.repository.jpa.AppUserRepository;
import ru.rsreu.lab.service.AppUserService;

import java.util.Optional;

@Service
@ConditionalOnProperty(name = "app.db-type", havingValue = "postgres")
@RequiredArgsConstructor
public class AppUserServiceImplJpa implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByLogin(login).orElseThrow();
    }

    @Override
    public Optional<AppUser> findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }

    @Override
    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }
}
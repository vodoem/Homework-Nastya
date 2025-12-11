package ru.rsreu.lab.service.impl.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.document.AppUserDocument;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.repository.mongo.AppUserRepository;
import ru.rsreu.lab.service.AppUserService;

import java.util.Optional;

@Service
@ConditionalOnProperty(name = "app.db-type", havingValue = "mongo")
@RequiredArgsConstructor
public class AppUserServiceImplMongo implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByLogin(login)
                .map(this::toEntity)
                .orElseThrow();
    }

    @Override
    public Optional<AppUser> findByLogin(String login) {
        return appUserRepository.findByLogin(login)
                .map(this::toEntity);
    }

    @Override
    public AppUser save(AppUser user) {
        AppUserDocument document = toDocument(user);
        AppUserDocument saved = appUserRepository.save(document);
        return toEntity(saved);
    }

    private AppUser toEntity(AppUserDocument document) {
        return AppUser.builder()
                .id(Long.parseLong(document.getId()))
                .login(document.getLogin())
                .password(document.getPassword())
                .build();
    }

    private AppUserDocument toDocument(AppUser user) {
        return AppUserDocument.builder()
                .id(user.getId() != null ? String.valueOf(user.getId()) : null)
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
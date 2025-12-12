package ru.rsreu.lab.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.dto.SignInRequestDTO;
import ru.rsreu.lab.model.dto.SignUpRequestDTO;
import ru.rsreu.lab.model.entity.AppUser;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserServiceImplJpa appUserService;
    private final PasswordEncoder encoder;

    public AppUser signUp(SignUpRequestDTO requestDTO) {
        Optional<AppUser> user = appUserService.findByLogin(requestDTO.getLogin());
        if (user.isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }

        String password = encoder.encode(requestDTO.getPassword());

        AppUser newUser = AppUser.builder()
                .login(requestDTO.getLogin())
                .password(password)
                .build();

        return appUserService.save(newUser);
    }

    public AppUser signIn(SignInRequestDTO requestDTO) {
        Optional<AppUser> userOpt = appUserService.findByLogin(requestDTO.getLogin());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        AppUser user = userOpt.get();

        if (!encoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(user.getLogin(), null, List.of());

        SecurityContextHolder.getContext().setAuthentication(auth);

        return user;
    }
}
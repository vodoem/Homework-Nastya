package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.dto.request.JwtRequestDTO;
import ru.rsreu.lab.model.dto.response.JwtResponseDTO;
import ru.rsreu.lab.model.entity.AppUser;
import ru.rsreu.lab.util.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserServiceImplJpa userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO signIn(JwtRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new UsernameNotFoundException(String.format("Неверный логин %s или пароль %s", authRequest.getLogin(), authRequest.getPassword()));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLogin());
        AppUser user = userService.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с логином %s не найден", authRequest.getLogin())));

        String accessToken = jwtTokenUtils.generateAccessToken(userDetails, user.getId());
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails, user.getId());

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    public JwtResponseDTO signUp(JwtRequestDTO registerRequest) throws BadRequestException {
        if (userService.findByLogin(registerRequest.getLogin()).isPresent()) {
            throw new BadRequestException(String.format("Пользователь с таким логином %s уже существует", registerRequest.getLogin()));
        }

        AppUser appUser = appUserCreating(registerRequest);

        AppUser savedUser = userService.save(appUser);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getLogin());

        String accessToken = jwtTokenUtils.generateAccessToken(userDetails, savedUser.getId());
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails, savedUser.getId());

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    private AppUser appUserCreating(JwtRequestDTO registerRequest) {
        return AppUser.builder()
                .login(registerRequest.getLogin())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
    }

}
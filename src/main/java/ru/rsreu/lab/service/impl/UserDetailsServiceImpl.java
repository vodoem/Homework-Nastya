package ru.rsreu.lab.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.model.UserDetailsImpl;
import ru.rsreu.lab.model.entity.AppUser;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserServiceImplJpa appUserService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AppUser user = appUserService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new UserDetailsImpl(user);
    }
}
/*
package ru.rsreu.lab.service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.lab.entity.AppUser;
import ru.rsreu.lab.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    public Optional<AppUser> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
*/

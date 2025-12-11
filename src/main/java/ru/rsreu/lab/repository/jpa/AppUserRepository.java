package ru.rsreu.lab.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.lab.model.entity.AppUser;


import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByLogin(String login);
}

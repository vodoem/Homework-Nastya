package ru.rsreu.lab.controller.mvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.model.dto.SignInRequestDTO;
import ru.rsreu.lab.model.dto.SignUpRequestDTO;
import ru.rsreu.lab.service.AuthMvcService;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthMvcController {
    private final AuthMvcService authService;

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new SignUpRequestDTO());
        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new SignInRequestDTO());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid SignUpRequestDTO requestDTO, Model model) {
        try {
            authService.signUp(requestDTO);
            model.addAttribute("successMessage", "Регистрация прошла успешно! Теперь вы можете войти.");
            return "redirect:/auth/login?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registration";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid SignInRequestDTO requestDTO, Model model) {
        try {
            authService.signIn(requestDTO);
            model.addAttribute("successMessage", "Вход выполнен успешно!");
            return "redirect:/secure/home?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }

}


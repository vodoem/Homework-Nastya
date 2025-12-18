package ru.rsreu.lab.controller.mvc;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rsreu.lab.model.dto.client.RestClientFormatForm;
import ru.rsreu.lab.model.dto.client.RestClientLoginForm;
import ru.rsreu.lab.model.dto.request.HistoryRequestDTO;
import ru.rsreu.lab.model.dto.request.JwtRequestDTO;
import ru.rsreu.lab.model.dto.response.FormatDTO;
import ru.rsreu.lab.model.dto.response.HistoryResponseDTO;
import ru.rsreu.lab.model.dto.response.JwtResponseDTO;
import ru.rsreu.lab.service.RestClientService;

import java.util.List;

@Controller
@RequestMapping("/rest-client")
@RequiredArgsConstructor
public class RestClientController {

    private static final String TOKEN_SESSION_ATTRIBUTE = "restClientToken";

    private final RestClientService restClientService;

    @GetMapping
    public String showClientPage(Model model, HttpSession session) {
        prepareModel(model, session);
        return "rest-client";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") RestClientLoginForm loginForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            addValidationAttributes(redirectAttributes, bindingResult, loginForm, "loginForm");
            return "redirect:/rest-client";
        }

        try {
            JwtResponseDTO tokens = restClientService.authenticate(
                    JwtRequestDTO.builder()
                            .login(loginForm.getLogin())
                            .password(loginForm.getPassword())
                            .build()
            );

            session.setAttribute(TOKEN_SESSION_ATTRIBUTE, tokens.getAccessToken());
            redirectAttributes.addFlashAttribute("successMessage", "Токен получен, можно вызывать REST методы.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/rest-client";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("loginForm") RestClientLoginForm loginForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        if (bindingResult.hasErrors()) {
            addValidationAttributes(redirectAttributes, bindingResult, loginForm, "loginForm");
            return "redirect:/rest-client";
        }

        try {
            JwtResponseDTO tokens = restClientService.register(
                    JwtRequestDTO.builder()
                            .login(loginForm.getLogin())
                            .password(loginForm.getPassword())
                            .build()
            );

            session.setAttribute(TOKEN_SESSION_ATTRIBUTE, tokens.getAccessToken());
            redirectAttributes.addFlashAttribute("successMessage", "Регистрация успешна, токен сохранён.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/rest-client";
    }

    @PostMapping("/format")
    public String formatText(@Valid @ModelAttribute("formatForm") RestClientFormatForm formatForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if (bindingResult.hasErrors()) {
            addValidationAttributes(redirectAttributes, bindingResult, formatForm, "formatForm");
            return "redirect:/rest-client";
        }

        String token = (String) session.getAttribute(TOKEN_SESSION_ATTRIBUTE);
        if (token == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Сначала выполните вход или регистрацию через REST.");
            return "redirect:/rest-client";
        }

        try {
            HistoryResponseDTO response = restClientService.formatText(
                    token,
                    HistoryRequestDTO.builder()
                            .originalText(formatForm.getOriginalText())
                            .formatId(formatForm.getFormatId())
                            .build()
            );

            redirectAttributes.addFlashAttribute("result", response.getFormattedText());
            redirectAttributes.addFlashAttribute("historyResponse", response);
            redirectAttributes.addFlashAttribute("successMessage", "Текст успешно отформатирован через REST API.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        redirectAttributes.addFlashAttribute("formatForm", formatForm);
        return "redirect:/rest-client";
    }

    @PostMapping("/logout")
    public String clearToken(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute(TOKEN_SESSION_ATTRIBUTE);
        redirectAttributes.addFlashAttribute("successMessage", "Токен REST-клиента удалён.");
        return "redirect:/rest-client";
    }

    @ModelAttribute("loginForm")
    public RestClientLoginForm loginForm() {
        return new RestClientLoginForm();
    }

    @ModelAttribute("formatForm")
    public RestClientFormatForm formatForm() {
        return new RestClientFormatForm();
    }

    private void prepareModel(Model model, HttpSession session) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new RestClientLoginForm());
        }

        if (!model.containsAttribute("formatForm")) {
            model.addAttribute("formatForm", new RestClientFormatForm());
        }

        String token = (String) session.getAttribute(TOKEN_SESSION_ATTRIBUTE);
        model.addAttribute("accessToken", token);

        if (token != null) {
            try {
                List<FormatDTO> formats = restClientService.loadFormats(token);
                model.addAttribute("formats", formats);
            } catch (Exception ex) {
                model.addAttribute("formatsError", ex.getMessage());
            }
        }
    }

    private void addValidationAttributes(RedirectAttributes redirectAttributes,
                                         BindingResult bindingResult,
                                         Object form,
                                         String attributeName) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + attributeName, bindingResult);
        redirectAttributes.addFlashAttribute(attributeName, form);
    }
}

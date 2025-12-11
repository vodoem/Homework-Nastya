package ru.rsreu.lab.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInRequestDTO {
    private String login;
    private String password;
}

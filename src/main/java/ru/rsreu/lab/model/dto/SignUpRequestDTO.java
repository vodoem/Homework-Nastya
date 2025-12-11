package ru.rsreu.lab.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequestDTO {
    private String login;
    private String password;
}

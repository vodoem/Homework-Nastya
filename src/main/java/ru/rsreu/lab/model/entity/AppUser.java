package ru.rsreu.lab.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AppUser")
public class AppUser {
    public static final String DEFAULT_GENERATOR = "default_seq";

    // вынесу в абстракт
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DEFAULT_GENERATOR)
    private Long id;

    private String login;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<History> history = new ArrayList<>();
}

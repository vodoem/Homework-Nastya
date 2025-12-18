package ru.rsreu.lab.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.rsreu.lab.client")
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientApplication.class);
        app.setAdditionalProfiles("client");
        app.run(args);
    }
}


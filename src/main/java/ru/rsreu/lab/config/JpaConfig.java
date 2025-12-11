package ru.rsreu.lab.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Configuration
@ConditionalOnProperty(name = "app.db-type", havingValue = "postgres")
@EnableJpaRepositories(
        basePackages = "ru.rsreu.lab.repository.jpa",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Mongo.*")
)
public class JpaConfig {}
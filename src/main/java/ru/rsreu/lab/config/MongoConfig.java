package ru.rsreu.lab.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(name = "app.db-type", havingValue = "mongo")
@EnableMongoRepositories(
        basePackages = "ru.rsreu.lab.repository.mongo"
)
public class MongoConfig {}
package ru.example.max.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.example.max.service.UserService;
import ru.example.max.util.UserValidator;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
}

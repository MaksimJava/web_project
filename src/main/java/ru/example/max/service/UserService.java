package ru.example.max.service;

import ru.example.max.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserFromEmail(String email);

    void add(User user);
}

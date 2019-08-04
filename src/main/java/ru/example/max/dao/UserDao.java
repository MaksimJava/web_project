package ru.example.max.dao;

import ru.example.max.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    User getUserFromEmail(String email);

    void add(User user);
}

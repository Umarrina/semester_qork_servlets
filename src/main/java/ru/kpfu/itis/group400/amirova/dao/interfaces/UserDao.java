package ru.kpfu.itis.group400.amirova.dao;

import ru.kpfu.itis.group400.amirova.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    void save(String login, String password, String email, String role);

    User getUserByLogin(String login);
}

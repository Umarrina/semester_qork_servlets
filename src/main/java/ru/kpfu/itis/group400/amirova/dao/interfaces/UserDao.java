package ru.kpfu.itis.group400.amirova.dao.interfaces;

import ru.kpfu.itis.group400.amirova.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    void save(User user);

    User getUserByLogin(String login);

    void update(User user);
    
    void delete(User user);
}

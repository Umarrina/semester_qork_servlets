package ru.kpfu.itis.group400.amirova.service;

import ru.kpfu.itis.group400.amirova.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(String id);
    Optional<UserDto> getUserByLogin(String login);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(String id, UserDto userDto);
    void deleteUser(String id);
    boolean validateUser(String login, String password);
}
package ru.kpfu.itis.group400.amirova.service.impl;

import ru.kpfu.itis.group400.amirova.dao.interfaces.UserDao;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.service.UserService;
import ru.kpfu.itis.group400.amirova.util.PasswordUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(String id) {
        return userDao.getAll().stream()
                .filter(user -> user.getId().toString().equals(id))
                .findFirst()
                .map(this::convertToDto);
    }

    @Override
    public Optional<UserDto> getUserByLogin(String login) {
        User user = userDao.getUserByLogin(login);
        return user != null ? Optional.of(convertToDto(user)) : Optional.empty();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User(
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getBio(),
                userDto.getProfilePhoto()
        );
        user.setRole(userDto.getRole());

        userDao.save(user);

        User savedUser = userDao.getUserByLogin(user.getLogin());
        return convertToDto(savedUser);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.update(user);
        return userDto;
    }

    @Override
    public void deleteUser(String id) {
        Optional<UserDto> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            User user = convertToEntity(userOptional.get());
            userDao.delete(user);
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    @Override
    public boolean validateUser(String login, String password) {
        Optional<UserDto> user = getUserByLogin(login);
        return user.get().getPassword().equals(password);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getBio(),
                user.getProfilePhoto()
        );
    }

    private User convertToEntity(UserDto userDto) {
        return new User(
                userDto.getId().toString(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRole(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getBio(),
                userDto.getProfilePhoto()
        );
    }
}
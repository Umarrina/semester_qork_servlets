package ru.kpfu.itis.group400.amirova.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePhoto;

    public User() {
        this.id = UUID.randomUUID(); // Генерируем ID по умолчанию
    }

    public User(String login, String password, String email,
                String username, String firstName,
                String lastName, String bio,
                String profilePhoto) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profilePhoto = profilePhoto;
        this.role = Role.USER;
    }

    public User(String id, String login, String password, String email,
                Role role, String username, String firstName,
                String lastName, String bio,
                String profilePhoto) {
        this.id = UUID.fromString(id);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profilePhoto = profilePhoto;

    }

    public User(String login, String password, String email,
                Role role, String username, String firstName,
                String lastName, String bio) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.id = UUID.randomUUID();

    }

    public String getLogin() {
        return login;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

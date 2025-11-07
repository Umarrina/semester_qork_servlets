package ru.kpfu.itis.group400.amirova.dto;

import ru.kpfu.itis.group400.amirova.entity.Role;

import java.util.UUID;

public class UserDto {
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

    public UserDto() {}

    public UserDto(UUID id, String login, String password, String email, Role role,
                   String username, String firstName, String lastName, String bio, String profilePhoto) {
        this.id = id;
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

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
}
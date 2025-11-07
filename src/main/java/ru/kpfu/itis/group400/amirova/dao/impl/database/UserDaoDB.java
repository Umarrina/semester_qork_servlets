package ru.kpfu.itis.group400.amirova.dao.impl.database;

import ru.kpfu.itis.group400.amirova.dao.interfaces.UserDao;
import ru.kpfu.itis.group400.amirova.entity.Role;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoDB implements UserDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    int intRole = resultSet.getInt("role");
                    Role role = Role.USER;
                    switch (intRole) {
                        case 1:
                            role = Role.USER;
                            break;
                        case 2:
                            role = Role.ADMIN;
                            break;
                    }
                    users.add(
                            new User(
                                    resultSet.getString("id"),
                                    resultSet.getString("login"),
                                    resultSet.getString("password"),
                                    resultSet.getString("email"),
                                    role,
                                    resultSet.getString("username"),
                                    resultSet.getString("firstname"),
                                    resultSet.getString("lastname"),
                                    resultSet.getString("bio"),
                                    resultSet.getString("profilePhoto")
                            ));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        String sql = "insert into users (id, login, password, email, role, username, firstname, lastname, bio, profilephoto) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getRole() == Role.ADMIN ? 2 : 1);
            preparedStatement.setString(6, user.getUsername());
            preparedStatement.setString(7, user.getFirstName());
            preparedStatement.setString(8, user.getLastName());
            preparedStatement.setString(9, user.getBio());
            preparedStatement.setString(10, user.getProfilePhoto());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        String sql = "select * from users where login = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Role role = Role.USER;
                int roleId = resultSet.getInt("role");
                switch (roleId) {
                    case 1:
                        role = Role.USER;
                        break;
                    case 2:
                        role = Role.ADMIN;
                        break;
                }

                return new User(
                        resultSet.getString("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        role,
                        resultSet.getString("username"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("bio"),
                        resultSet.getString("profilephoto")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            connection.setAutoCommit(false);

            String deleteTracksSituationsSQL = "DELETE FROM tracks_situations WHERE id_user_suggest = ?";
            PreparedStatement deleteTracksSituationsStmt = connection.prepareStatement(deleteTracksSituationsSQL);
            deleteTracksSituationsStmt.setString(1, user.getId().toString());
            deleteTracksSituationsStmt.executeUpdate();

            String deleteSituationsSQL = "DELETE FROM situation WHERE user_id = ?";
            PreparedStatement deleteSituationsStmt = connection.prepareStatement(deleteSituationsSQL);
            deleteSituationsStmt.setString(1, user.getId().toString());
            deleteSituationsStmt.executeUpdate();

            String deleteTracksSQL = "DELETE FROM track WHERE author = ?";
            PreparedStatement deleteTracksStmt = connection.prepareStatement(deleteTracksSQL);
            deleteTracksStmt.setString(1, user.getId().toString());
            deleteTracksStmt.executeUpdate();

            String deleteUserSQL = "DELETE FROM users WHERE id = ?";
            PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserSQL);
            deleteUserStmt.setString(1, user.getId().toString());

            int rowsAffected = deleteUserStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("User not found for deletion");
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
            }
            throw new RuntimeException("Error deleting user and related data", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET login = ?, password = ?, email = ?, username = ?, firstname = ?, lastname = ?, bio = ?, profilephoto = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getBio());
            preparedStatement.setString(8, user.getProfilePhoto());
            preparedStatement.setString(9, user.getId().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

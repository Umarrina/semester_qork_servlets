package ru.kpfu.itis.group400.amirova.dao.impl.database;

import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.entity.Track;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SituationDaoDB implements SituationDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public List<Situation> getAll() {
        String sql = "select * from situation";
        List<Situation> situations = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Situation situation = new Situation();
                situation.setId(resultSet.getInt("id"));
                situation.setUserId(UUID.fromString(resultSet.getString("user_id")));
                situation.setTitle(resultSet.getString("title"));
                situation.setDescription(resultSet.getString("description"));
                situation.setDate(resultSet.getTimestamp("date"));
                situation.setApproved(resultSet.getBoolean("approved"));
                situations.add(situation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return situations;
    }

    @Override
    public void save(Situation situation) {
        String sql = "INSERT INTO situation (user_id, title, description, date, approved) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, situation.getUserId().toString());
            preparedStatement.setString(2, situation.getTitle());
            preparedStatement.setString(3, situation.getDescription());
            preparedStatement.setTimestamp(4, situation.getDate());
            preparedStatement.setBoolean(5, situation.isApproved());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Situation getSituationById(int id) {
        String sql = "SELECT * FROM situation WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Situation situation = new Situation();
                situation.setId(resultSet.getInt("id"));
                situation.setUserId(UUID.fromString(resultSet.getString("user_id")));
                situation.setTitle(resultSet.getString("title"));
                situation.setDescription(resultSet.getString("description"));
                situation.setDate(resultSet.getTimestamp("date"));
                situation.setApproved(resultSet.getBoolean("approved"));
                situation.setTracks(getTracksBySituationId(id));
                return situation;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Track> getTracksBySituationId(int id) {
        String sql = "SELECT t.* FROM track t " +
                "INNER JOIN tracks_situations ts ON t.id = ts.id_track " +
                "WHERE ts.id_situation = ? AND t.approved = true AND ts.approved = true";
        List<Track> tracks = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setAuthor(resultSet.getString("author"));
                track.setFilePath(resultSet.getString("filepath"));
                track.setApproved(resultSet.getBoolean("approved"));
                tracks.add(track);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracks;

    }

    @Override
    public List<Situation> getSituationsByUser(User user) {
        String sql = "SELECT * FROM situation WHERE user_id = ? ORDER BY id DESC";
        List<Situation> situations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Situation situation = new Situation();
                situation.setId(resultSet.getInt("id"));
                situation.setUserId(UUID.fromString(resultSet.getString("user_id")));
                situation.setTitle(resultSet.getString("title"));
                situation.setDescription(resultSet.getString("description"));
                situation.setDate(resultSet.getTimestamp("date"));
                situation.setApproved(resultSet.getBoolean("approved"));
                situations.add(situation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return situations;
    }

    @Override
    public void update(Situation situation) {
        String sql = "UPDATE situation SET title = ?, description = ?, date = ?, approved = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, situation.getTitle());
            preparedStatement.setString(2, situation.getDescription());
            preparedStatement.setTimestamp(3, situation.getDate());
            preparedStatement.setBoolean(4, situation.isApproved());
            preparedStatement.setInt(5, situation.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM situation WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Situation not found for deletion");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting situation", e);
        }
    }
}
package ru.kpfu.itis.group400.amirova.dao.impl.database;

import ru.kpfu.itis.group400.amirova.dao.interfaces.TrackDao;
import ru.kpfu.itis.group400.amirova.entity.Track;
import ru.kpfu.itis.group400.amirova.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackDaoDB implements TrackDao {

    Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public List<Track> getAll() {
        String sql = "SELECT * FROM track";
        List<Track> tracks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
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
    public List<Track> getAllApproved() {
        String sql = "SELECT * FROM track WHERE approved = true";
        List<Track> tracks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
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
    public void save(Track track) {
        String sql = "INSERT INTO track (title, author, filepath, approved) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, track.getTitle());
            preparedStatement.setString(2, track.getAuthor());
            preparedStatement.setString(3, track.getFilePath());
            preparedStatement.setBoolean(4, track.getApproved());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Track getTrackById(int id) {
        String sql = "SELECT * FROM track WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Track track = new Track();
                    track.setId(resultSet.getInt("id"));
                    track.setTitle(resultSet.getString("title"));
                    track.setAuthor(resultSet.getString("author"));
                    track.setFilePath(resultSet.getString("filepath"));
                    track.setApproved(resultSet.getBoolean("approved"));
                    return track;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Track> getTracksByIds(List<Integer> trackIds) {
        if (trackIds == null || trackIds.isEmpty()) {
            return new ArrayList<>();
        }

        String placeholders = String.join(",", Collections.nCopies(trackIds.size(), "?"));
        String sql = "SELECT * FROM track WHERE id IN (" + placeholders + ")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < trackIds.size(); i++) {
                preparedStatement.setInt(i + 1, trackIds.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();

            while (resultSet.next()) {
                Track track = new Track(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("filepath")
                );
                tracks.add(track);
            }
            return tracks;

        } catch (SQLException e) {
            throw new RuntimeException("Error getting tracks by IDs", e);
        }
    }

    @Override
    public void update(Track track) {
        String sql = "UPDATE track SET title = ?, author = ?, filepath = ?, approved = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, track.getTitle());
            preparedStatement.setString(2, track.getAuthor());
            preparedStatement.setString(3, track.getFilePath());
            preparedStatement.setBoolean(4, track.getApproved());
            preparedStatement.setInt(5, track.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Track not found for update");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating track", e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            connection.setAutoCommit(false);

            String deleteTracksSituationsSQL = "DELETE FROM tracks_situations WHERE id_track = ?";
            try (PreparedStatement deleteTracksSituationsStmt = connection.prepareStatement(deleteTracksSituationsSQL)) {
                deleteTracksSituationsStmt.setInt(1, id);
                deleteTracksSituationsStmt.executeUpdate();
            }

            String deleteTrackSQL = "DELETE FROM track WHERE id = ?";
            try (PreparedStatement deleteTrackStmt = connection.prepareStatement(deleteTrackSQL)) {
                deleteTrackStmt.setInt(1, id);

                int rowsAffected = deleteTrackStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("Track not found for deletion");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error deleting track", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
}

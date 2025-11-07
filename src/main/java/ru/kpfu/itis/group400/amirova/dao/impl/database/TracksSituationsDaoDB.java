package ru.kpfu.itis.group400.amirova.dao.impl.database;

import ru.kpfu.itis.group400.amirova.dao.interfaces.TracksSituationsDao;
import ru.kpfu.itis.group400.amirova.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TracksSituationsDaoDB implements TracksSituationsDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public void save(int situationId, int trackId, UUID userId) {
        String checkSql = "SELECT 1 FROM tracks_situations WHERE id_situation = ? AND id_track = ? AND id_user_suggest = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setInt(1, situationId);
            checkStatement.setInt(2, trackId);
            checkStatement.setString(3, userId.toString());

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking existing track-situation relation", e);
        }

        String insertSql = "INSERT INTO tracks_situations (id_situation, id_track, id_user_suggest, approved) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setInt(1, situationId);
            insertStatement.setInt(2, trackId);
            insertStatement.setString(3, userId.toString());
            insertStatement.setBoolean(4, false);

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving track-situation relation", e);
        }
    }

    @Override
    public void delete(int situationId, int trackId) {
        String sql = "DELETE FROM tracks_situations WHERE id_situation = ? AND id_track = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, situationId);
            preparedStatement.setInt(2, trackId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Track-situation relation not found for deletion");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting track-situation relation", e);
        }
    }

    @Override
    public List<Integer> findTrackIdsBySituationId(int situationId) {
        String sql = "SELECT id_track FROM tracks_situations WHERE id_situation = ?";
        List<Integer> trackIds = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, situationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trackIds.add(resultSet.getInt("id_track"));
            }
            return trackIds;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding tracks for situation", e);
        }
    }

    @Override
    public List<Integer> findSituationIdsByTrackId(int trackId) {
        String sql = "SELECT id_situation FROM tracks_situations WHERE id_track = ?";
        List<Integer> situationIds = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trackId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                situationIds.add(resultSet.getInt("id_situation"));
            }
            return situationIds;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding situations for track", e);
        }
    }

    @Override
    public boolean exists(int situationId, int trackId) {
        String sql = "SELECT 1 FROM tracks_situations WHERE id_situation = ? AND id_track = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, situationId);
            preparedStatement.setInt(2, trackId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking track-situation relation", e);
        }
    }

    @Override
    public boolean exists(int situationId, int trackId, boolean approved) {
        String sql = "SELECT 1 FROM tracks_situations WHERE id_situation = ? AND id_track = ? AND approved = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, situationId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.setBoolean(3, approved);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking track-situation relation", e);
        }
    }

    @Override
    public void updateApproval(int situationId, int trackId, boolean approved) {
        String sql = "UPDATE tracks_situations SET approved = ? WHERE id_situation = ? AND id_track = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, approved);
            preparedStatement.setInt(2, situationId);
            preparedStatement.setInt(3, trackId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Track-situation relation not found for update");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating track-situation approval", e);
        }
    }

    public List<Integer> findApprovedTrackIdsBySituationId(int situationId) {
        String sql = "SELECT id_track FROM tracks_situations WHERE id_situation = ? AND approved = true";
        List<Integer> trackIds = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, situationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trackIds.add(resultSet.getInt("id_track"));
            }
            return trackIds;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding approved tracks for situation", e);
        }
    }
}

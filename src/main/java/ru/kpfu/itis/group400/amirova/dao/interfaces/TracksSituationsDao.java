package ru.kpfu.itis.group400.amirova.dao.interfaces;

import java.util.List;
import java.util.UUID;

public interface TracksSituationsDao {

    void save(int situationId, int trackId, UUID userId);

    void delete(int situationId, int trackId);

    List<Integer> findTrackIdsBySituationId(int situationId);

    List<Integer> findSituationIdsByTrackId(int trackId);

    boolean exists(int situationId, int trackId);

    void updateApproval(int situationId, int trackId, boolean approved);

    boolean exists(int situationId, int trackId, boolean approved);
}

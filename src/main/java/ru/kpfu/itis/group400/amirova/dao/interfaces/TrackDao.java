package ru.kpfu.itis.group400.amirova.dao.interfaces;

import ru.kpfu.itis.group400.amirova.entity.Track;

import java.util.List;

public interface TrackDao {
    public List<Track> getAll();

    public List<Track> getAllApproved();

    public void save(Track track);

    public Track getTrackById(int id);

    List<Track> getTracksByIds(List<Integer> trackIds);

    void update(Track track);
    void delete(int id);
}


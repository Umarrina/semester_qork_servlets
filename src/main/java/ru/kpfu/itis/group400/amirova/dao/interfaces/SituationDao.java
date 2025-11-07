package ru.kpfu.itis.group400.amirova.dao.interfaces;

import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.entity.Track;
import ru.kpfu.itis.group400.amirova.entity.User;

import java.util.List;

public interface SituationDao {

    List<Situation> getAll();

    void save(Situation situation);

    Situation getSituationById(int id);

    List<Track> getTracksBySituationId(int id);

    List<Situation> getSituationsByUser(User user);

    void update(Situation situation);

    void delete(int id);
}

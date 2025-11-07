package ru.kpfu.itis.group400.amirova.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Situation {
    private int id;
    private UUID userId;
    private String title;
    private String description;
    private Timestamp date;
    private boolean approved;
    private List<Track> tracks;

    public Situation(UUID userId, String title, String description, Timestamp date) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.approved = false;
    }

    public Situation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}

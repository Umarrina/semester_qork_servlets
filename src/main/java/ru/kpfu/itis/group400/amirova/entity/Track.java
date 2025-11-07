package ru.kpfu.itis.group400.amirova.entity;

public class Track {
    private int id;
    private String title;
    private String author;
    private String filePath;
    private boolean approved;

    public Track() {
    }

    public Track(int id, String title, String author, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }


}

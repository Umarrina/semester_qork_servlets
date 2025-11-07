package ru.kpfu.itis.group400.amirova.dto;

public class TrackDto {
    private int id;
    private String title;
    private String author;
    private String filePath;
    private boolean approved;

    public TrackDto() {}

    public TrackDto(int id, String title, String author, String filePath, boolean approved) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.filePath = filePath;
        this.approved = approved;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
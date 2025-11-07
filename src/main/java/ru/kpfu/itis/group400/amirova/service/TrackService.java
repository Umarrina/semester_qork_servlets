package ru.kpfu.itis.group400.amirova.service;

import ru.kpfu.itis.group400.amirova.dto.TrackDto;

import java.util.List;

public interface TrackService {
    List<TrackDto> getAllTracks();
    List<TrackDto> getApprovedTracks();
    TrackDto getTrackById(int id);
    TrackDto createTrack(TrackDto trackDto);
    TrackDto updateTrack(int id, TrackDto trackDto);
    void deleteTrack(int id);
    List<TrackDto> searchTracks(String query);
    List<TrackDto> getTracksByIds(List<Integer> trackIds);
}
package ru.kpfu.itis.group400.amirova.service.impl;

import ru.kpfu.itis.group400.amirova.dao.interfaces.TrackDao;
import ru.kpfu.itis.group400.amirova.dto.TrackDto;
import ru.kpfu.itis.group400.amirova.entity.Track;
import ru.kpfu.itis.group400.amirova.service.TrackService;

import java.util.List;
import java.util.stream.Collectors;

public class TrackServiceImpl implements TrackService {

    private final TrackDao trackDao;

    public TrackServiceImpl(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public List<TrackDto> getAllTracks() {
        return trackDao.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> getApprovedTracks() {
        return trackDao.getAll().stream()
                .filter(Track::getApproved)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrackDto getTrackById(int id) {
        Track track = trackDao.getTrackById(id);
        return track != null ? convertToDto(track) : null;
    }

    @Override
    public TrackDto createTrack(TrackDto trackDto) {
        Track track = convertToEntity(trackDto);
        trackDao.save(track);
        return convertToDto(track);
    }

    @Override
    public TrackDto updateTrack(int id, TrackDto trackDto) {
        Track track = trackDao.getTrackById(id);
        if (track != null) {
            track.setTitle(trackDto.getTitle());
            track.setAuthor(trackDto.getAuthor());
            track.setFilePath(trackDto.getFilePath());
            track.setApproved(trackDto.isApproved());
            trackDao.update(track);
        }
        return convertToDto(track);
    }

    @Override
    public void deleteTrack(int id) {
        trackDao.delete(id);
    }

    @Override
    public List<TrackDto> searchTracks(String query) {
        return trackDao.getAll().stream()
                .filter(track -> track.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        track.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TrackDto convertToDto(Track track) {
        return new TrackDto(
                track.getId(),
                track.getTitle(),
                track.getAuthor(),
                track.getFilePath(),
                track.getApproved()
        );
    }

    private Track convertToEntity(TrackDto trackDto) {
        Track track = new Track();
        track.setId(trackDto.getId());
        track.setTitle(trackDto.getTitle());
        track.setAuthor(trackDto.getAuthor());
        track.setFilePath(trackDto.getFilePath());
        track.setApproved(trackDto.isApproved());
        return track;
    }

    @Override
    public List<TrackDto> getTracksByIds(List<Integer> trackIds) {
        List<Track> tracks = trackDao.getTracksByIds(trackIds);
        return tracks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
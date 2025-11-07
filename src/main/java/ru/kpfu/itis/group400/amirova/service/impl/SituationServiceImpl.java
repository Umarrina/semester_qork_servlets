package ru.kpfu.itis.group400.amirova.service.impl;

import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.dto.SituationDto;
import ru.kpfu.itis.group400.amirova.dto.TrackDto;
import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.service.SituationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SituationServiceImpl implements SituationService {

    private final SituationDao situationDao;

    public SituationServiceImpl(SituationDao situationDao) {
        this.situationDao = situationDao;
    }

    @Override
    public List<SituationDto> getAllSituations() {
        return situationDao.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SituationDto> getApprovedSituations() {
        return situationDao.getAll().stream()
                .filter(Situation::isApproved)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SituationDto getSituationById(int id) {
        Situation situation = situationDao.getSituationById(id);
        return situation != null ? convertToDto(situation) : null;
    }

    @Override
    public List<SituationDto> getSituationsByUserId(UUID userId) {
        User user = new User(); // Create minimal user object
        user.setId(userId);
        return situationDao.getSituationsByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SituationDto createSituation(SituationDto situationDto) {
        Situation situation = convertToEntity(situationDto);
        situationDao.save(situation);
        return convertToDto(situation);
    }

    @Override
    public SituationDto updateSituation(int id, SituationDto situationDto) {
        Situation situation = convertToEntity(situationDto);
        situation.setId(id);
        situationDao.update(situation);
        return situationDto;
    }

    @Override
    public void deleteSituation(int id) {
        situationDao.delete(id);
    }

    @Override
    public void approveSituation(int id) {
        Situation situation = situationDao.getSituationById(id);
        if (situation != null) {
            situation.setApproved(true);
            situationDao.update(situation);
        }
    }

    private SituationDto convertToDto(Situation situation) {
        List<TrackDto> trackDtos = situation.getTracks() != null ?
                situation.getTracks().stream()
                        .map(track -> new TrackDto(track.getId(), track.getTitle(), track.getAuthor(),
                                track.getFilePath(), track.getApproved()))
                        .collect(Collectors.toList()) : null;

        return new SituationDto(
                situation.getId(),
                situation.getUserId(),
                situation.getTitle(),
                situation.getDescription(),
                situation.getDate(),
                situation.isApproved(),
                trackDtos
        );
    }

    private Situation convertToEntity(SituationDto situationDto) {
        Situation situation = new Situation(
                situationDto.getUserId(),
                situationDto.getTitle(),
                situationDto.getDescription(),
                situationDto.getDate()
        );
        situation.setId(situationDto.getId());
        situation.setApproved(situationDto.isApproved());
        return situation;
    }
}
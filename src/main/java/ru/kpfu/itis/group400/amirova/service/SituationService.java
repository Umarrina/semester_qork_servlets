package ru.kpfu.itis.group400.amirova.service;

import ru.kpfu.itis.group400.amirova.dto.SituationDto;

import java.util.List;
import java.util.UUID;

public interface SituationService {
    List<SituationDto> getAllSituations();
    List<SituationDto> getApprovedSituations();
    SituationDto getSituationById(int id);
    List<SituationDto> getSituationsByUserId(UUID userId);
    SituationDto createSituation(SituationDto situationDto);
    SituationDto updateSituation(int id, SituationDto situationDto);
    void deleteSituation(int id);
    void approveSituation(int id);
}
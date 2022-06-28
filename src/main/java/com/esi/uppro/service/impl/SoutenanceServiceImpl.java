package com.esi.uppro.service.impl;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.domain.Eleve;
import com.esi.uppro.domain.Soutenance;
import com.esi.uppro.repository.AnneeAcademiqueRepository;
import com.esi.uppro.repository.SoutenanceRepository;
import com.esi.uppro.service.SoutenanceService;
import com.esi.uppro.service.dto.AdminStatisticsDTO;
import com.esi.uppro.service.dto.ReportDTO;
import com.esi.uppro.service.dto.SoutenanceDTO;
import com.esi.uppro.service.mapper.SoutenanceMapper;
import com.esi.uppro.utils.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Soutenance}.
 */
@Service
@Transactional
public class SoutenanceServiceImpl implements SoutenanceService {

    private final Logger log = LoggerFactory.getLogger(SoutenanceServiceImpl.class);

    private final SoutenanceRepository soutenanceRepository;

    @Autowired
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    private final SoutenanceMapper soutenanceMapper;

    public SoutenanceServiceImpl(SoutenanceRepository soutenanceRepository, SoutenanceMapper soutenanceMapper) {
        this.soutenanceRepository = soutenanceRepository;
        this.soutenanceMapper = soutenanceMapper;
    }

    @Override
    public SoutenanceDTO save(SoutenanceDTO soutenanceDTO) {
        log.debug("Request to save Soutenance : {}", soutenanceDTO);
        Soutenance soutenance = soutenanceMapper.toEntity(soutenanceDTO);
        soutenance = soutenanceRepository.save(soutenance);
        return soutenanceMapper.toDto(soutenance);
    }

    @Override
    public SoutenanceDTO update(SoutenanceDTO soutenanceDTO) {
        log.debug("Request to save Soutenance : {}", soutenanceDTO);
        Soutenance soutenance = soutenanceMapper.toEntity(soutenanceDTO);
        soutenance = soutenanceRepository.save(soutenance);
        return soutenanceMapper.toDto(soutenance);
    }

    @Override
    public Optional<SoutenanceDTO> partialUpdate(SoutenanceDTO soutenanceDTO) {
        log.debug("Request to partially update Soutenance : {}", soutenanceDTO);

        return soutenanceRepository
            .findById(soutenanceDTO.getId())
            .map(existingSoutenance -> {
                soutenanceMapper.partialUpdate(existingSoutenance, soutenanceDTO);

                return existingSoutenance;
            })
            .map(soutenanceRepository::save)
            .map(soutenanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoutenanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Soutenances");
        return soutenanceRepository.findAll(pageable).map(soutenanceMapper::toDto);
    }

    public Page<SoutenanceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return soutenanceRepository.findAllWithEagerRelationships(pageable).map(soutenanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoutenanceDTO> findOne(Long id) {
        log.debug("Request to get Soutenance : {}", id);
        return soutenanceRepository.findOneWithEagerRelationships(id).map(soutenanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Soutenance : {}", id);
        soutenanceRepository.deleteById(id);
    }

    @Override
    public AdminStatisticsDTO findAdminStatistics() {
        //ZoneId zoneId = ZoneId.of("US/Central");
        AdminStatisticsDTO adminStatisticsDTO = new AdminStatisticsDTO();

        AnneeAcademique anneeAcademique = anneeAcademiqueRepository.findFirstByOrderByIdDesc();

        String years = anneeAcademique.getAnneeScolaire().trim();

        years = years.substring(years.lastIndexOf('-') + 1, years.length());
        years = years.trim();

        int annee = Integer.parseInt(years);

        LocalDate startDate = LocalDate.of(annee, 1, 1);
        LocalDate endDate = LocalDate.of(annee + 1, 1, 1);
        int eleveSoutenus = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        int soutenanceAjourne = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        int rapportRendu = soutenanceRepository.nbreRapportRendu(startDate, endDate);
        int mentionPassable = soutenanceRepository.nbreSoutenanceMention(startDate, endDate, 10.0, 12.0);
        int mentionAssezBien = soutenanceRepository.nbreSoutenanceMention(startDate, endDate, 12.0, 14.0);
        int mentionBien = soutenanceRepository.nbreSoutenanceMention(startDate, endDate, 14.0, 16.0);
        int mentionTresBien = soutenanceRepository.nbreSoutenanceMention(startDate, endDate, 16.0, 18.0);

        startDate = LocalDate.of(annee, 7, 1);
        endDate = LocalDate.of(annee, 8, 1);
        int soutenanceJuillet = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);

        startDate = LocalDate.of(annee, 10, 1);
        endDate = LocalDate.of(annee, 11, 1);
        int soutenanceOctobre = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);

        startDate = LocalDate.of(annee, 2, 1);
        endDate = LocalDate.of(annee, 3, 1);
        int soutenanceFevrier = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);

        startDate = LocalDate.of(annee, 3, 1);
        endDate = LocalDate.of(annee, 4, 1);
        int soutenanceMars = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);

        adminStatisticsDTO.setNbreEleveSoutenus(eleveSoutenus);
        adminStatisticsDTO.setNbreEleveAjourne(soutenanceAjourne);
        adminStatisticsDTO.setNbreRapportRendus(rapportRendu);
        adminStatisticsDTO.setNbreMentionPassable(mentionPassable);
        adminStatisticsDTO.setNbreMentionAssezBien(mentionAssezBien);
        adminStatisticsDTO.setNbreMentionBien(mentionBien);
        adminStatisticsDTO.setNbreMentionTresBien(mentionTresBien);

        adminStatisticsDTO.setNbreSoutenuJuillet(soutenanceJuillet);
        adminStatisticsDTO.setNbreSoutenuOctobre(soutenanceOctobre);
        adminStatisticsDTO.setNbreSoutenuFevrier(soutenanceFevrier);
        adminStatisticsDTO.setNbreSoutenuMars(soutenanceMars);

        return adminStatisticsDTO;
    }

    public LocalDate[] findDateZone(String period) {
        //ZoneId zoneId = ZoneId.of("Africa/Abidjan");
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        String dateCourante = null; // DateUtils.dateToString(startDate, "dd/MM/yyyy");

        switch (period) {
            case "daily":
                startDate = LocalDate.from(today);
                endDate = LocalDate.from(today.plusDays(1));

                dateCourante = DateUtils.dateToString(startDate, "dd/MM/yyyy");
                break;
            case "weekly": // du 22/07/2020 00:00:00 au 29/07/2020 00:00:00
                startDate = LocalDate.from(today.minusDays(6)); // du 23/07/2020 00:00:00 au 30/07/2020 00:00:00
                endDate = startDate.plusDays(7);

                dateCourante =
                    "du " +
                    DateUtils.dateToString(startDate, "dd/MM/yyyy") +
                    " au " +
                    DateUtils.dateToString(endDate.minusDays(1), "dd/MM/yyyy");
                break;
            case "year": // du 22/07/2020 00:00:00 au 22/07/2021 00:00:00
                startDate = LocalDate.from(today.minusDays(364)); // du 23/07/2020 00:00:00 au 30/07/2020 00:00:00
                endDate = startDate.plusDays(365);

                dateCourante =
                    "du " +
                    DateUtils.dateToString(startDate, "dd/MM/yyyy") +
                    " au " +
                    DateUtils.dateToString(endDate.minusDays(1), "dd/MM/yyyy");
                break;
            default:
                startDate = null;
                endDate = null;
                break;
        }

        return new LocalDate[] { startDate, endDate };
    }
}

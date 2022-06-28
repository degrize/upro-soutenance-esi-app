package com.esi.uppro.service.impl;

import com.esi.uppro.domain.User;
import com.esi.uppro.repository.EleveRepository;
import com.esi.uppro.repository.SoutenanceRepository;
import com.esi.uppro.service.ReportService;
import com.esi.uppro.service.dto.ReportDTO;
import com.esi.uppro.utils.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private SoutenanceRepository soutenanceRepository;

    public ReportServiceImpl() {}

    @Override
    public ReportDTO adminDailyReport(String period) {
        ReportDTO reportDTO = new ReportDTO();

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

        reportDTO.setDateCourante(dateCourante);

        int totalEleveValideSoutenance = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        reportDTO.setTotalEleveValideSoutenance(totalEleveValideSoutenance);
        int totaEleveSoutenus = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);
        reportDTO.setTotaEleveSoutenus(totaEleveSoutenus);
        int totalEleveAjournee = soutenanceRepository.nbresoutenancevAjournee(startDate, endDate);
        reportDTO.setTotalEleveAjournee(totalEleveAjournee);

        return reportDTO;
    }

    @Override
    public ReportDTO adminDailyReport(String period, User user) {
        ReportDTO reportDTO = new ReportDTO();

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

        reportDTO.setDateCourante(dateCourante);

        int totalEleveValideSoutenance = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        reportDTO.setTotalEleveValideSoutenance(totalEleveValideSoutenance);
        int totaEleveSoutenus = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);
        reportDTO.setTotaEleveSoutenus(totaEleveSoutenus);
        int totalEleveAjournee = soutenanceRepository.nbresoutenancevAjournee(startDate, endDate);
        reportDTO.setTotalEleveAjournee(totalEleveAjournee);

        return reportDTO;
    }

    @Override
    public ReportDTO adminDailyReportBackOffice(String period, User user) {
        ReportDTO reportDTO = new ReportDTO();

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

        reportDTO.setDateCourante(dateCourante);

        int totalEleveValideSoutenance = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        reportDTO.setTotalEleveValideSoutenance(totalEleveValideSoutenance);
        int totaEleveSoutenus = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);
        reportDTO.setTotaEleveSoutenus(totaEleveSoutenus);
        int totalEleveAjournee = soutenanceRepository.nbresoutenancevAjournee(startDate, endDate);
        reportDTO.setTotalEleveAjournee(totalEleveAjournee);

        return reportDTO;
    }

    @Override
    public ReportDTO adminDailyReportManager(String period, User user) {
        ReportDTO reportDTO = new ReportDTO();

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

        reportDTO.setDateCourante(dateCourante);

        int totalEleveValideSoutenance = soutenanceRepository.nbresoutenancevalide(startDate, endDate);
        reportDTO.setTotalEleveValideSoutenance(totalEleveValideSoutenance);
        int totaEleveSoutenus = soutenanceRepository.countByDateDuJourBetween(startDate, endDate);
        reportDTO.setTotaEleveSoutenus(totaEleveSoutenus);
        int totalEleveAjournee = soutenanceRepository.nbresoutenancevAjournee(startDate, endDate);
        reportDTO.setTotalEleveAjournee(totalEleveAjournee);

        return reportDTO;
    }
}

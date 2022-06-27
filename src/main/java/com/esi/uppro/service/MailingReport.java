package com.esi.uppro.service;

import com.esi.uppro.domain.User;
import com.esi.uppro.repository.UserRepository;
import com.esi.uppro.service.dto.ReportDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailingReport {

    private final Logger log = LoggerFactory.getLogger(MailingReport.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportService reportService;

    public MailingReport() {}

    /* @Scheduled(cron="00 00 18 * * MON-FRI")
    public void dailyReport() {
        log.info("Start daily report");
        Authority authority = new Authority();
        Set<Authority> authorities = new HashSet<>();
        authority.setName("ROLE_ADMIN");
        authorities.add(authority);

        Set<User> users = userRepository.findByAuthoritiesContainsAndActivated(authorities, true);
        ReportDTO reportDTO = reportService.adminDailyReport("daily");

        mailService.sendReportEmail(users, reportDTO);
    }

    @Scheduled(cron = "00 00 18 * * FRI") // execute every friday at 16:30:00
    public void weeklyReport() throws Exception {
        log.info("Start weekly report");
        Authority authority = new Authority();
        Set<Authority> authorities = new HashSet<>();
        authority.setName("ROLE_ADMIN");
        authorities.add(authority);

        Set<User> users = userRepository.findByAuthoritiesContainsAndActivated(authorities, true);
        ReportDTO reportDTO = reportService.adminDailyReport("weekly");

        mailService.sendReportEmail(users, reportDTO);
    } */

    @Scheduled(cron = "00 30 23 * * MON-FRI")
    public void dailyReport() {
        log.info("Start daily report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();
        exclusions.add("dga-esi@inphb.ci");
        exclusions.add("dg-esi@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));
        for (User user : users) {
            log.info(user.toString());
            if (user.getAuthoritiesCSV().toLowerCase().contains("admin")) {
                reportDTO = reportService.adminDailyReport("daily");
                mailService.sendReportEmail(user, reportDTO);
            } else if (user.getAuthoritiesCSV().toLowerCase().contains("directeur_etude")) {
                reportDTO = reportService.adminDailyReportManager("daily", user);
                mailService.sendReportEmail(user, reportDTO);
            } else if (user.getAuthoritiesCSV().toLowerCase().contains("inspecteur")) {
                reportDTO = reportService.adminDailyReportBackOffice("daily", user);
                mailService.sendReportEmail(user, reportDTO);
            }
        }
    }

    //@Scheduled(cron = "00 30 23 * * FRI") // execute every friday at 16:30:00
    @Scheduled(cron = "0 0/5 14 * * ?") // every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
    public void weeklyReport() {
        log.info("Start weekly report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();
        exclusions.add("dga-esi@inphb.ci");
        exclusions.add("dg-esi@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));
        for (User user : users) {
            log.info(user.toString());
            if (user.getAuthoritiesCSV().toLowerCase().contains("admin")) {
                reportDTO = reportService.adminDailyReport("weekly");
                mailService.sendReportEmail(user, reportDTO);
            } else if (user.getAuthoritiesCSV().toLowerCase().contains("directeur_etude")) {
                reportDTO = reportService.adminDailyReportManager("weekly", user);
                mailService.sendReportEmail(user, reportDTO);
            } else if (user.getAuthoritiesCSV().toLowerCase().contains("inspecteur")) {
                reportDTO = reportService.adminDailyReportBackOffice("weekly", user);
                mailService.sendReportEmail(user, reportDTO);
            }
        }
    }

    //@Scheduled(cron = "00 30 23 * * FRI") // execute every friday at 16:30:00
    @Scheduled(cron = "0 0/5 14 * * ?") // every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
    public void weeklyAlertDepoRapport() {
        log.info("Start weekly report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();
        exclusions.add("dga-esi@inphb.ci");
        exclusions.add("dg-esi@inphb.ci");
        exclusions.add("dga@inphb.ci");
        exclusions.add("dg@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));
        for (User user : users) {
            log.info(user.toString());
            if (user.getAuthoritiesCSV().toLowerCase().contains("eleve")) {
                mailService.sendReportEmailDepotRapport(user);
            } else if (user.getAuthoritiesCSV().toLowerCase().contains("prof_encadreur")) {
                mailService.sendReportEmailDepotRapport(user);
            }
        }
    }
}

package com.esi.uppro.service;

import com.esi.uppro.domain.User;
import com.esi.uppro.service.dto.ReportDTO;

public interface ReportService {
    ReportDTO adminDailyReport(String period);

    ReportDTO adminDailyReport(String period, User user);

    ReportDTO adminDailyReportManager(String period, User user);

    ReportDTO adminDailyReportBackOffice(String period, User user);
}

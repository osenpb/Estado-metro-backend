package com.osen.back_estado_metro.util;

import com.osen.back_estado_metro.dtos.ReportDTO;
import com.osen.back_estado_metro.models.Report;

public class Mapper {

    public static ReportDTO toReportDTO(Report report) {
        return new ReportDTO(
                report.getStation().getId(),
                report.getUser().getId(),
                report.getStatus().getId()
        );
    }
}

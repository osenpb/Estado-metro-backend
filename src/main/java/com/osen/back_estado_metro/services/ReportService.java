package com.osen.back_estado_metro.services;

import com.osen.back_estado_metro.dtos.ReportDTO;
import com.osen.back_estado_metro.models.Report;

import java.util.List;

public interface ReportService {

    List<ReportDTO> findAll();
    Report save(ReportDTO reportDTO);
    Report findById(Long id);
    Boolean update(Report report);
    void deleteById(Long id);
    List<Report> findByStationName(String stationName);
    String findStatusMostVotedByStationName(String stationName);


}

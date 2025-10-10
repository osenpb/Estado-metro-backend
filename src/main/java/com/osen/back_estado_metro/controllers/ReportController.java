package com.osen.back_estado_metro.controllers;

import com.osen.back_estado_metro.dtos.ReportDTO;
import com.osen.back_estado_metro.models.Report;
import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.services.ReportService;
import com.osen.back_estado_metro.util.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReports() {

        List<ReportDTO> reportList = reportService.findAll();

        return ResponseEntity.ok(reportList);
    }

    @GetMapping("/station")
    public ResponseEntity<?> getReportsByStationName(@RequestParam String stationName){
        List<Report> reportList = reportService.findByStationName(stationName);

        return ResponseEntity.ok(reportList);

    }

    @PostMapping("/new")
    public ResponseEntity<ReportDTO> saveReport(@RequestBody ReportDTO reportDto) {

        Report savedReport = reportService.save(reportDto);
        return ResponseEntity.ok(Mapper.toReportDTO(savedReport)); //
    }


}

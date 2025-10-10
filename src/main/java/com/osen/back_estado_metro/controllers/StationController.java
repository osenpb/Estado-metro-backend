package com.osen.back_estado_metro.controllers;

import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.services.ReportService;
import com.osen.back_estado_metro.services.StationService;

import com.osen.back_estado_metro.services.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    private final StationService stationService;
    private final ReportService reportService;
    private final StatusService statusService;

    public StationController(StationService stationService, ReportService reportService, StatusService statusService) {
        this.stationService = stationService;
        this.reportService = reportService;
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Station>> getAllStations() {

        List<Station> stationsList = stationService.findAll();
        return ResponseEntity.of(Optional.ofNullable(stationsList));
    }

    @GetMapping("/status")
    public ResponseEntity<Station> getMostVotedStatusByStation(@RequestParam String stationName) {
        String mostVotedStatus = reportService.findStatusMostVotedByStationName(stationName);
        Station foundStation = stationService.findByStationName(stationName);

        Status newStatus = statusService.findByStatusName(mostVotedStatus);

        log.info("Estatus conseguido: {}", newStatus);
        foundStation.setStatus(newStatus);
        stationService.update(foundStation);
        return ResponseEntity.ok(foundStation);

    }
}

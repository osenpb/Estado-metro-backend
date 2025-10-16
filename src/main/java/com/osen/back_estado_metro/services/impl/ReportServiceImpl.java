package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.dtos.ReportDTO;
import com.osen.back_estado_metro.handlerException.StationNotFoundException;
import com.osen.back_estado_metro.handlerException.StatusNotFoundException;
import com.osen.back_estado_metro.handlerException.UserNotFoundException;
import com.osen.back_estado_metro.models.Report;
import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.models.User;
import com.osen.back_estado_metro.repositories.ReportRepository;
import com.osen.back_estado_metro.repositories.StationRepository;
import com.osen.back_estado_metro.repositories.StatusRepository;
import com.osen.back_estado_metro.repositories.UserRepository;
import com.osen.back_estado_metro.services.ReportService;
import com.osen.back_estado_metro.util.Mapper;
import jakarta.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final StatusRepository statusRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, StationRepository stationRepository, StatusRepository statusRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDTO> findAll() {
        List<Report> reportList = reportRepository.findAll();
        return reportList.stream().map(Mapper::toReportDTO).toList();
    }

    @Override
    public Report save(ReportDTO reportDTO) {

        User user = userRepository.findById(reportDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        Station station = stationRepository.findById(reportDTO.stationId())
                .orElseThrow(() -> new StationNotFoundException("Estación no encontrada"));

        Status statusFounded;
        try {
            statusFounded = statusRepository.findById(reportDTO.statusId()).orElseThrow(() -> new RuntimeException("No se encontro el status"));
        } catch (Exception e) {
            throw new StatusNotFoundException("Error en el status ingresado");
        }


        Report newReport = Report.builder()
                .station(station)
                .user(user)
                .timestamp(LocalDateTime.now())
                .status(statusFounded)
                .build();

        return reportRepository.save(newReport);
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Boolean update(Report report) {

        if (reportRepository.existsById(report.getId())) {
            reportRepository.save(report);
            return true;
        }

        return false;
    }

    @Override
    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }

    /**
    * Este metodo solo lo uso de manera interna para el mostVotedStatus
    * */
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByStationName(String stationName) {
        return reportRepository.findByStation_StationName(stationName);

    }

    @Override
    public String findStatusMostVotedByStationName(String stationName) {

        List<Report> reportList = findByStationName(stationName);

        if (reportList.isEmpty()) {
            return "VACIO";
        }

        int vacioCount = 0;
        int aceptableCount = 0;
        int llenoCount = 0;

        for (Report report : reportList) {
            Status statusFounded = report.getStatus();
            String statusOption = statusFounded.getStatusName();

            switch (statusOption) {
                case "VACIO" -> vacioCount++;
                case "ACEPTABLE" -> aceptableCount++;
                case "LLENO" -> llenoCount++;
            }
        }

        if (vacioCount >= aceptableCount && vacioCount >= llenoCount) {
            return "VACIO";
        } else if (aceptableCount >= vacioCount && aceptableCount >= llenoCount) {
            return "ACEPTABLE";
        } else {
            return "LLENO";
        }
    }

}

package com.osen.back_estado_metro.repositories;

import com.osen.back_estado_metro.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByStation_StationName(String stationName);

}

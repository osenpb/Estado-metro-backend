package com.osen.back_estado_metro.repositories;

import com.osen.back_estado_metro.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByStationName(String stationName);
}

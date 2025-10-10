package com.osen.back_estado_metro.services;

import com.osen.back_estado_metro.models.Station;

import java.util.List;

public interface StationService {

    List<Station> findAll();
    Station save(Station station);
    Station findById(Long id);
    Boolean update(Station station );
    void deleteById(Long id);
    Station findByStationName(String stationName);


}

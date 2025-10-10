package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.repositories.StationRepository;
import com.osen.back_estado_metro.services.StationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    @Override
    public Station save(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station findById(Long id) {
        return stationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Boolean update(Station station) {
        if(stationRepository.existsById(station.getId())){
            stationRepository.save(station);
            return true;
        }

        return false;
    }

    @Override
    public void deleteById(Long id) {
        stationRepository.deleteById(id);

    }

    @Override
    public Station findByStationName(String stationName) {
        return stationRepository.findByStationName(stationName);
    }
}

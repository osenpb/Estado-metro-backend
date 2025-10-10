package com.osen.back_estado_metro.services.impl;

import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.repositories.StatusRepository;
import com.osen.back_estado_metro.services.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status findById(Long id) {

        return statusRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Status no encontrado"));

    }

    @Override
    public Boolean update(Status status) {
        if(statusRepository.existsById(status.getId())){
            statusRepository.save(status);
            return true;
        }
        return false;
    }

    @Override
    public Status findByStatusName(String statusName) {
        return statusRepository.findByStatusName(statusName);
    }
}

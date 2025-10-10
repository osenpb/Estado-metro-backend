package com.osen.back_estado_metro.services;

import com.osen.back_estado_metro.models.Status;


import java.util.List;

public interface StatusService {

    List<Status> findAll();
    Status save(Status status);
    Status findById(Long id);
    Boolean update(Status status);
    Status findByStatusName(String statusName);
}

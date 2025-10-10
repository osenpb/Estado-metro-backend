package com.osen.back_estado_metro.repositories;

import com.osen.back_estado_metro.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusName(String statusName);
}

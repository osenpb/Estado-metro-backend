package com.osen.back_estado_metro.controllers;


import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.services.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Status>> getAllStatus(){

        List<Status> statusList = statusService.findAll();

        return ResponseEntity.ok(statusList);
    }

}

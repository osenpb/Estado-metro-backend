package com.osen.back_estado_metro.handlerException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException exception){
        ErrorResponse userNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "User Not Found");
        log.error("Usuario no encontrado");
        return new ResponseEntity<>(userNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<?> stationNotFound(StationNotFoundException exception){
        ErrorResponse stationNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Station Not Found");
        log.error("Estacion no encontrada");
        return new ResponseEntity<>(stationNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<?> statusNotFound(StatusNotFoundException exception){
        ErrorResponse statusNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Status Not Found");
        log.error("Estacion no encontrada");
        return new ResponseEntity<>(statusNotFound, HttpStatus.NOT_FOUND);
    }

}

package com.osen.back_estado_metro.handlerException;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message){
        super(message);
    }

}

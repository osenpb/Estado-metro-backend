package com.osen.back_estado_metro.handlerException;

import java.time.LocalDateTime;

public record ErrorResponse(

        LocalDateTime timestamp,
        String message,
        String details

) {
}

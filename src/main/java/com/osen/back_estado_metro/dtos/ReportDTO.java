package com.osen.back_estado_metro.dtos;

import com.osen.back_estado_metro.models.Status;

public record ReportDTO(
        Long userId,
        Long stationId,
        Long statusId
) {
}

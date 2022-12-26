package com.elvir.backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
public class AppointmentDto {

    private List<Map<LocalDate, LocalTime>> freeTimes;
}

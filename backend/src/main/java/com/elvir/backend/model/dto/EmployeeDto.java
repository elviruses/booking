package com.elvir.backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class EmployeeDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private LocalTime startTime;

    private LocalTime endTime;
}
